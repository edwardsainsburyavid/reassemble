package org.edwardsainsbury.reassembler;

public abstract class AbstractReassembler {

    public abstract String getReassembled();

    public abstract void addFragment(String fragment);

    public abstract void removeFragment(String fragment);
}
