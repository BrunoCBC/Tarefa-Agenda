CREATE TABLE IF NOT EXISTS agenda(
    codigo int not null auto_increment,
    nome varchar(45) not null,
    primary key(codigo)
);

CREATE TABLE IF NOT EXISTS telefone(
    telefone int not null,
    codigo int not null,
    primary key(telefone),
    foreign key(codigo) references agenda(codigo)
);

CREATE TABLE IF NOT EXISTS email(
    email varchar(100) not null,
    codigo int not null,
    primary key(email),
    foreign key(codigo) references agenda(codigo)
);