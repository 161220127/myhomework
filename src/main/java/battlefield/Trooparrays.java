package battlefield;

enum Shape{鹤翼, 雁行, 衡轨, 鱼鳞, 方门, 偃月, 锋矢,长蛇}


public class Trooparrays {

    public class Troop{
        public Shape shape;
        public int num;
        public int length;
        public int width;
        public int []x = new int[30];
        public int []y = new int[30];
        Troop(){
            num = 0;
            length = 0;
            width = 0;
            for(int i = 0; i < 30;i ++){
                x[i] = -1;
                y[i] = -1;
            }
        }
    }
    private Troop []troops = new Troop[8];
    public Trooparrays() {
        for (int i = 0; i < 8; i++) {
            troops[i] = new Troop();
        }
        int []shapex0 = {0, 1, 1, 2, 2, 3, 3, 4};
        int []shapey0 = {0, -1, 1, -2, 2, -1, 1, 0};
        troops[0].shape =  Shape.values()[0];
        troops[0].num = 8;
        troops[0].length = 5;
        troops[0].width = 5;
        System.arraycopy(shapex0, 0, troops[0].x, 0, 8);
        System.arraycopy(shapey0, 0, troops[0].y, 0, 8);

        int []shapex1 = {0, 1, 2, 3, 4, 5, 6, 7};
        int []shapey1 = {0, -1, 0, -1, 0, -1, 0, -1};
        troops[1].shape = Shape.values()[1];
        troops[1].num = 8;
        troops[1].length = 8;
        troops[1].width = 2;
        System.arraycopy(shapex1, 0, troops[1].x, 0, troops[1].num);
        System.arraycopy(shapey1, 0, troops[1].y, 0, troops[1].num);

        int []shapex2 = {0, 1, 2, 3, 4, 5, 6, 7};
        int []shapey2 = {0, -1, -2, -3, -3, -2, -1, 0};
        troops[2].shape = Shape.values()[2];
        troops[2].num = 8;
        troops[2].length = 8;
        troops[2].width = 4;
        System.arraycopy(shapex2, 0, troops[2].x, 0, 8);
        System.arraycopy(shapey2, 0, troops[2].y, 0, 8);

        int []shapex3 = {0, 1, 2, 3, 4, 5, 6, 7};
        int []shapey3 = {0, 0, 0, 0, 0, 0, 0, 0};
        troops[3].shape = Shape.values()[3];
        troops[3].num = 8;
        troops[3].length = 8;
        troops[3].width = 1;
        System.arraycopy(shapex3, 0, troops[3].x, 0, troops[3].num);
        System.arraycopy(shapey3, 0, troops[3].y, 0, troops[3].num);

        int []shapex4 = {0, 1, 1, 2, 2, 3, 3, 4};
        int []shapey4 = {0, -1, 1, -2, 2, -1, 1, 0};
        troops[4].shape = Shape.values()[4];
        troops[4].num = 8;
        troops[4].length = 5;
        troops[4].width = 5;
        System.arraycopy(shapex4, 0, troops[4].x, 0, 8);

        System.arraycopy(shapey4, 0, troops[4].y, 0, 8);

        int []shapex5 = {0, 1, 2, 3, 4, 5, 6, 7};
        int []shapey5 = {0, 1, 0, 1, 0, 1, 0, 1};
        troops[5].shape = Shape.values()[5];
        troops[5].num = 8;
        troops[5].length = 8;
        troops[5].width = 2;
        System.arraycopy(shapex5, 0, troops[5].x, 0, 8);
        System.arraycopy(shapey5, 0, troops[5].y, 0, 8);

        int []shapex6 = {0, 1, 2, 3, 4, 5, 6, 7};
        int []shapey6 = {0, 1, 2, 3, 3, 2, 1, 0};
        troops[6].shape = Shape.values()[6];
        troops[6].num = 8;
        troops[6].length = 8;
        troops[6].width = 4;
        System.arraycopy(shapex6, 0, troops[6].x, 0, troops[6].num);
        System.arraycopy(shapey6, 0, troops[6].y, 0, troops[6].num);

        int []shapex7 = {0, 1, 2, 3, 4, 5, 6, 7};
        int []shapey7 = {0, 0, 0, 0, 0, 0, 0, 0};
        troops[7].shape = Shape.values()[7];
        troops[7].num = 8;
        troops[7].length = 8;
        troops[7].width = 1;
        System.arraycopy(shapex7, 0, troops[7].x, 0, troops[7].num);
        System.arraycopy(shapey7, 0, troops[7].y, 0, troops[7].num);
    }
    public Troop choose(int i) {
        return troops[i];
    }
}
