-- Drop table

-- DROP TABLE public.accounts;

CREATE TABLE public.accounts (
	id serial NOT NULL,
	"name" varchar(30) NULL,
	balance numeric(12,2) NULL,
	status varchar NOT NULL,
	CONSTRAINT accounts_check CHECK ((balance >= (0)::numeric)),
	CONSTRAINT accounts_pkey PRIMARY KEY (id)
);

CREATE TABLE public.accounts_association (
	userid varchar(30) NULL,
	id int4 NULL
);


-- public.accounts_association foreign keys

ALTER TABLE public.accounts_association ADD CONSTRAINT accounts_association_fk FOREIGN KEY (id) REFERENCES accounts(id) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE public.accounts_association ADD CONSTRAINT accounts_association_fk_1 FOREIGN KEY (userid) REFERENCES users(userid) ON UPDATE CASCADE ON DELETE CASCADE;


CREATE TABLE public.users (
	userid varchar(30) NOT NULL,
	first_name varchar(30) NULL,
	last_name varchar(30) NULL,
	"password" varchar(30) NULL,
	roles varchar(10) NULL,
	CONSTRAINT users_pk PRIMARY KEY (userid)
);

create or replace function addNewAccount(newName character varying, newBalance double precision , newUser character varying) returns Integer as
$$
begin
insert into accounts (name, balance, status) values (newName , newBalance , 'pending');
insert into accounts_association (userid, id) values (newUser, lastval());
return lastval();
end;
$$ language plpgsql;


ALTER TABLE public.accounts ADD CONSTRAINT accounts_check CHECK ((balance >= (0)::numeric));




