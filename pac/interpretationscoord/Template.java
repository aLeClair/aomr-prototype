package pac.interpretationscoord;


//Both archetypes and domainspecs implement template class so they can be used interchangeably for the respective functions
interface Template {

    public String getName();

    public void addAttr(String s);

    public Boolean remAttr(String s);

    public Boolean modifyAttr(String s1, String s2);

    public String[] getAttrs();
}
