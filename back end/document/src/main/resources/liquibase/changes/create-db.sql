create table "users" (
                        id serial not null,
                        username varchar(100) not null unique ,
                        email varchar(255) not null unique ,
                        first_name varchar(100) not null,
                        last_name varchar(100) not null,
                        password varchar(100) not null,
                        status varchar(25) not null default 'ACTIVE',
                        created TIMESTAMP default CURRENT_TIMESTAMP,
                        updated TIMESTAMP default CURRENT_TIMESTAMP,
                        primary key (id)
);

create table "roles" (
                         id serial not null,
                         name varchar(100) not null unique ,
                         created TIMESTAMP default CURRENT_TIMESTAMP,
                         updated TIMESTAMP default CURRENT_TIMESTAMP,
                         primary key (id)
);

create table "documents" (
                         id serial not null,
                         title varchar(100) not null,
                         description varchar(255) not null,
                         filename varchar(255) not null,
                         status varchar(25) not null default 'ACTIVE',
                         user_id int,
                         created TIMESTAMP default CURRENT_TIMESTAMP,
                         updated TIMESTAMP default CURRENT_TIMESTAMP,
                         primary key (id),
                         CONSTRAINT fk_user
                             FOREIGN KEY(user_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE
);

create table "comments" (
                             id serial not null,
                             comment varchar(255) not null,
                             status varchar(25) not null default 'ACTIVE',
                             user_id int,
                             document_id int,
                             created TIMESTAMP default CURRENT_TIMESTAMP,
                             updated TIMESTAMP default CURRENT_TIMESTAMP,
                             primary key (id),
                             CONSTRAINT fk_user
                                 FOREIGN KEY(user_id)
                                     REFERENCES users(id)
                                     ON DELETE CASCADE,
                             CONSTRAINT fk_document
                                 FOREIGN KEY(document_id)
                                     REFERENCES documents(id)
                                     ON DELETE CASCADE
);

create table "user_roles" (
                            user_id int,
                            role_id int,
                            CONSTRAINT fk_user
                                FOREIGN KEY(user_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE,
                            CONSTRAINT fk_role
                                FOREIGN KEY(role_id)
                                    REFERENCES roles(id)
                                    ON DELETE CASCADE
);

insert into roles (id, name)
values (1, 'ROLE_USER');

insert into roles (id, name)
values (2, 'ROLE_ADMIN');

create table "marks" (
                            id  serial not null,
                            mark varchar(25) not null,
                            status varchar(25) not null default 'ACTIVE',
                            user_id int,
                            document_id int,
                            created TIMESTAMP default CURRENT_TIMESTAMP,
                            updated TIMESTAMP default CURRENT_TIMESTAMP,
                            primary key (id),
                            CONSTRAINT fk_user
                                FOREIGN KEY(user_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE,
                            CONSTRAINT fk_document
                                FOREIGN KEY(document_id)
                                    REFERENCES documents(id)
                                    ON DELETE CASCADE
);


