package com.berteodosio.samplelitica.congressman.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "deputados")
public class CongressmanList {

    @ElementList(inline = true)
    private List<Congressman> congressmanList;

    public List<Congressman> list() {
        return congressmanList;
    }
}
