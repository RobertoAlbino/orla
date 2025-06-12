create table projeto (
    id serial primary key,
    nome varchar(120) not null,
    data_criacao date not null
);

create table funcionario (
    id serial primary key,
    nome varchar(120) not null,
    cpf varchar(11) not null unique,
    email varchar(255) not null unique,
    salario numeric(12,2) not null
);

create table projeto_funcionario (
    projeto_id int not null references projeto(id) on delete cascade,
    funcionario_id int not null references funcionario(id) on delete cascade,
    primary key (projeto_id, funcionario_id)
);
