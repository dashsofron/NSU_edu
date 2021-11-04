package GameModel;

import snakesProto.SnakesProto;

public class Coord {
    private Integer i;
    private Integer j;

    public Coord(Integer i, Integer j,int width,int height) {
        this.i= i;

        if (i >= width)
            this.i= width - i;
        if(i<0)
            this.i= width + i;
        this.j= j;
        if (j < 0) this.j= height+ j;
        if (j >= height) this.j=  height - j;

    }

    public Integer getI() {
        return i;
    }

    public Integer getJ() {
        return j;
    }

    public Integer getI(int width) {
        if (i >= width)
        return width - i;
        if(i<0)
            return width  + i;
        return i;
    }

    public Integer getJ(int height) {
        if (j < 0) return height + j;
        if (j >= height) return  height - j;
        return j;
    }

    public Integer getI(int width,int shift) {
        Integer iCoord = i+shift;

        if (iCoord >= width)
            return width - iCoord;
        if(iCoord<0)
            return width + iCoord;
        return iCoord;
    }

    public Integer getJ(int height,int shift) {
        Integer jCoord = j+shift;

        if (jCoord < 0) return height + jCoord;
        if (jCoord >= height) return  height - jCoord;
        return jCoord;
    }


    public SnakesProto.GameState.Coord getProtoCoord() {
        return SnakesProto.GameState.Coord.newBuilder()
                .setX(i)
                .setY(j)
                .build();
    }
}
