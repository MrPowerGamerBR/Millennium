package com.mrpowergamerbr.millennium.utils.blog;

import org.mongodb.morphia.annotations.Entity;
import lombok.*;

@Getter
@Setter
@Entity(value = "pages", noClassnameStored = true)
public class Page extends Post {} // Não tem diferença uma página para um post
// Tudo que um post tem já é o suficiente para uma página
// Então é meio que uma classe vazia
