package application;

public class side_effect {
             private int sideEffectID;
             private String name;
             private String severity;
             static side_effect cus = null;
			public side_effect() {
				super();
				// TODO Auto-generated constructor stub
			}

			public side_effect(int sideEffectID, String name, String severity) {
				super();
				this.sideEffectID = sideEffectID;
				this.name = name;
				this.severity = severity;
			}

			public int getSideEffectID() {
				return sideEffectID;
			}

			public void setSideEffectID(int sideEffectID) {
				this.sideEffectID = sideEffectID;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getSeverity() {
				return severity;
			}

			public void setSeverity(String severity) {
				this.severity = severity;
			}
			
             
}
