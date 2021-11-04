package GameModel;

public class MyField {
    private Integer width;
    private Integer height;
    private Integer[][] gameField;

    public MyField(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        gameField = new Integer[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                gameField[i][j] = -1;
    }

    public Integer getCell(Integer i, Integer j) {
        Integer iCoord = i;
        Integer jCoord = j;
        //System.out.println(i+" "+j);
        if (i < 0) iCoord = width - 1 + i;
        if (j < 0) jCoord = height - 1 + j;
        if (i >= width) iCoord = width - i;
        if (j >= height) jCoord = height - j;
        //System.out.println(iCoord+" "+jCoord);

        return gameField[iCoord][jCoord];


    }

    public void setCell(Integer i, Integer j, Integer value) {
        Integer iCoord = i;
        Integer jCoord = j;
        if (i < 0) iCoord = width - 1 + i;
        if (j < 0) jCoord = height - 1 + j;
        if (i >= width) iCoord = width - i;
        if (j >= height) jCoord = height - j;
        gameField[iCoord][jCoord] = value;
    }
}
