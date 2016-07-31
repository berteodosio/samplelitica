package com.berteodosio.samplelitica.congressman.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "comissoes")
public class Comission {

    @Element(name = "titular", required = false)
    private String holder;

    @Element(name = "suplente", required = false)
    private String surrogate;
}
