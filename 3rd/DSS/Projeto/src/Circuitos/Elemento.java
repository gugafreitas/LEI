package src.Circuitos;

public class Elemento{
        //private String circID;
        private int gdu;
        //private int id;
        private int tipo; // 1 para reta, 2 para curva, 3 para chicane

        public Elemento(){
            //this.circID = "";
            this.gdu = 0;
            //this.id = 0;
            this.tipo = 0;
        }

        public Elemento(/*String cID,int id,*/int gdu,int tipo){
            //this.circID = cID;
            this.gdu = gdu;
            //this.id = id;
            this.tipo = tipo;
        }

        public Elemento(Elemento c){
            //this.circID = c.getCircID();
            this.gdu = c.getGdu();
            //this.id = c.getId();
            this.tipo = c.getTipo();
        }

        /*public void setCircID(String cID){
            this.circID = cID;
        }

        public String getCircID() {
            return this.circID;
        }*/

        public void setGDU(int gdu){
            this.gdu = gdu;
        }

        public int getGdu(){
            return this.gdu;
        }

        /*public void setId(int id){
            this.id = id;
        }

        public int getId(){
            return this.id;
        }*/

        public void setTipo(int tipo){
            this.tipo = tipo;
        }

        public int getTipo(){
            return this.tipo;
        }
    }