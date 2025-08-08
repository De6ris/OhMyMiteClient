package com.github.temp.ommc.inventory.section;

public interface IContainer {
    void dc$setSectionHandler(SectionHandler sectionHandler);

    SectionHandler dc$getSectionHandler();

    String dc$getTypeString();
}
