package studentsinfo3.model;




public class Session {
    private Group rootGroup;

    private String name;

    private String server;

    public Session() {
    }

    public void setSessionDescription(String name, String server) {
      this.name = name;
      this.server = server;
    }

    public Group getRoot() {
      if (rootGroup == null)
        rootGroup = new Group(null, "RootGroup");
      return rootGroup;
    }

    public String getName() {
      return name;
    }

    public String getServer() {
      return server;
    }
  }