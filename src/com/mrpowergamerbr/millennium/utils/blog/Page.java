package com.mrpowergamerbr.millennium.utils.blog;

import org.mongodb.morphia.annotations.Entity;
import lombok.*;

@Getter
@Setter
@Entity(value = "pages", noClassnameStored = true)
public class Page extends Post {} // N�o tem diferen�a uma p�gina para um post
// Tudo que um post tem j� � o suficiente para uma p�gina
// Ent�o � meio que uma classe vazia
