package utils;

public enum Cat {
        Food("1"), Rent("2"), Gas("3"), Transport("4");
        private String id;

        private Cat(String id) {
                this.id = id;
        }
        
        public String getID(){
        	return id;
        }
        
        
	
}
