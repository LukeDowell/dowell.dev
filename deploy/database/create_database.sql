create table `user`(
    `id` int not null auto_increment,
    `username` varchar not null,
    `roles` varchar not null,
    `password` varchar not null,

    primary key (`id`)
)

create table `post`(
    `id` int not null auto_increment,
    `content` varchar not null,
    `timestamp` date not null,

    primary key (`id`)
)

engine = innodb
default charset = utf8;