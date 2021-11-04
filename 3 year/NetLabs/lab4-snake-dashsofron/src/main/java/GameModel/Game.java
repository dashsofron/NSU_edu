package GameModel;

import Handler.SendMessageHandler;
import snakesProto.SnakesProto;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static snakesProto.SnakesProto.Direction.*;

public class Game {
    int myID;
    private Boolean master = false;
    private GState state = new GState();
    private SendMessageHandler sendHandler;
    private Random rand = new Random();

    public Game(SendMessageHandler sendHandler) {

        this.sendHandler = sendHandler;
        //field=new int[width][height];
    }

    public int getMyID() {
        return myID;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }

    public void setMaster(Boolean master) {
        this.master = master;
        if (master) {
            for (GPlayer player : state.getPlayers()) {
                if (player.getId() == myID) {
                    player.setRole(SnakesProto.NodeRole.MASTER);
                    return;
                }
            }
        }
    }

    public void initGame() {
        state.setConfig();
        state.setField(new MyField(state.getConfig().getWidth(), state.getConfig().getHeight()));
    }

    public void removePlayerByID(int id) {
        int num = getPlayerNumById(id);
        state.getPlayers().remove(num);
    }

    public void updateState(SnakesProto.GameState state) {
        this.state.updateState(state);
    }


    public void changeSnakeDirection(SnakesProto.Direction dir, Integer snakeOwnerID) {
        setSnakeDirByID(snakeOwnerID, dir);
    }

    public void changeNodeRole(int id, SnakesProto.NodeRole role) {
        Integer num = getPlayerNumById(id);
        if (num == null) return;
        state.getPlayerByNum(id).setRole(role);

    }

    public Boolean haveDeputy() {
        for (int i = 0; i < state.getPlayers().size(); i++)
            if (state.getPlayers().get(i).getRole() == SnakesProto.NodeRole.DEPUTY) return true;
        return false;

    }

    public GPlayer getDeputy() {
        for (int i = 0; i < state.getPlayers().size(); i++)
            if (state.getPlayers().get(i).getRole() == SnakesProto.NodeRole.DEPUTY) return state.getPlayers().get(i);
        return null;

    }

    public GPlayer chooseAlivePlayer() {
        for (int i = 0; i < state.getPlayers().size(); i++)
            if (state.getPlayers().get(i).getRole() != SnakesProto.NodeRole.MASTER && state.getPlayers().get(i).getRole() != SnakesProto.NodeRole.DEPUTY && !state.getPlayers().get(i).getSnake().isDead() && state.getPlayers().get(i).getSnake().getSnakeState() == SnakesProto.GameState.Snake.SnakeState.ALIVE)
                return state.getPlayers().get(i);
        return null;

    }

    public void markDead(int id) {
        getState().getPlayerByNum(id).getSnake().setDeath();
    }

    public List<Coord> havePlaceForNewSnake() {
        List<Coord> coords = new ArrayList<>();
        for (int i = 0; i < state.getConfig().getWidth(); i++)
            for (int j = 0; j < state.getConfig().getHeight(); j++) {
                if (state.getField().getCell(i, j) == -1) {
                    if (state.getField().getCell(i - 1, j) >= 0 ||
                            state.getField().getCell(i + 1, j) >= 0 ||
                            state.getField().getCell(i, j - 1) >= 0 ||
                            state.getField().getCell(i, j + 1) >= 0)
                        continue;

                    if (state.getField().getCell(i - 1, j) == -1) {
                        coords.add(new Coord(i, j, state.getConfig().getWidth(), state.getConfig().getHeight()));
                        coords.add(new Coord(i - 1, j, state.getConfig().getWidth(), state.getConfig().getHeight()));
                        return coords;
                    } else if (state.getField().getCell(i + 1, j) == -1) {
                        coords.add(new Coord(i, j, state.getConfig().getWidth(), state.getConfig().getHeight()));
                        coords.add(new Coord(i + 1, j, state.getConfig().getWidth(), state.getConfig().getHeight()));
                        return coords;
                    } else if (state.getField().getCell(i, j - 1) == -1) {
                        coords.add(new Coord(i, j, state.getConfig().getWidth(), state.getConfig().getHeight()));
                        coords.add(new Coord(i, j - 1, state.getConfig().getWidth(), state.getConfig().getHeight()));
                        return coords;
                    } else if (state.getField().getCell(i, j + 1) == -1) {
                        coords.add(new Coord(i, j, state.getConfig().getWidth(), state.getConfig().getHeight()));
                        coords.add(new Coord(i, j + 1, state.getConfig().getWidth(), state.getConfig().getHeight()));
                        return coords;
                    }
                }

            }


        return null;
    }

    public void addSnake(List<Coord> coords, Integer id) {
        for (int i = 0; i < coords.size(); i++) {
            state.getField().setCell(coords.get(i).getI(), coords.get(i).getJ(), id);
        }
        state.getPlayerByNum(id).setSnake(new GSnake(SnakesProto.GameState.Snake.SnakeState.ALIVE, coords, getDirByCoords(coords)));
        state.getPlayerByNum(id).setDIr(getDirByCoords(coords));

    }

    public GPlayer getMaster() {
        for (GPlayer player : state.getPlayers()
        ) {
            if (player.getRole() == SnakesProto.NodeRole.MASTER) return player;

        }
        return null;
    }

    public boolean getMasterBool() {
        return master;
    }

    public InetSocketAddress getAddrById(int id) throws UnknownHostException {
        //return InetSocketAddress.createUnresolved(getPlayers().get(getPlayerNumById(id)).getIpAddress(),getPlayers().get(getPlayerNumById(id)).getPort());
        String name = getPlayers().get(getPlayerNumById(id)).getIpAddress();
        return new InetSocketAddress(InetAddress.getByName(name.substring(name.indexOf('/') + 1)), getPlayers().get(getPlayerNumById(id)).getPort());
        //System.out.println(new InetSocketAddress(getPlayers().get(getPlayerNumById(id)).getIpAddress(),getPlayers().get(getPlayerNumById(id)).getPort()) + " GET SOCKET ADDR");
        //return new InetSocketAddress(getPlayers().get(getPlayerNumById(id)).getIpAddress(),getPlayers().get(getPlayerNumById(id)).getPort());

    }

    public SnakesProto.Direction getDirByCoords(List<Coord> coords) {
        if (coords.get(0).getI().equals(coords.get(1).getI())) {
            if (coords.get(0).getJ() > coords.get(1).getJ())
                return UP;
            else return SnakesProto.Direction.DOWN;
        } else {
            if (coords.get(0).getI() > coords.get(1).getI())
                return SnakesProto.Direction.RIGHT;
            else return SnakesProto.Direction.LEFT;

        }
    }

    public void addPlayer(String name, Integer id, Integer port, String ip, SnakesProto.NodeRole role) {
        state.addPlayer(name, id, port, ip, role);
    }

    public void setSnakeDirByID(int id, SnakesProto.Direction dir) {
        state.getPlayerByNum(id).setDIr(dir);
        //state.getPlayerByNum(id).getSnake().setDirection(dir);
    }

    public void changeSnakeDir(int id) {
        SnakesProto.Direction prev = state.getPlayerByNum(id).getSnake().getDirection();
        SnakesProto.Direction current = state.getPlayerByNum(id).getDir();
        if (!(prev == UP && current == DOWN || prev == DOWN && current == UP || prev == RIGHT && current == LEFT || prev == LEFT && current == RIGHT))
            state.getPlayerByNum(id).getSnake().setDirection(current);
    }

    public int getAliveSnakesNum() {
        int num = 0;
        for (GPlayer player : state.getPlayers()
        ) {
            if (player.getSnake() != null && player.getSnake().getSnakeState() == SnakesProto.GameState.Snake.SnakeState.ALIVE)
                num++;

        }
        return num;
    }

    public Integer getPlayerNumById(int id) {
        for (int i = 0; i < state.getPlayers().size(); i++)
            if (state.getPlayers().get(i).getId() == id) return i;
        return null;
    }

    public void generateFood(int foodNum) {
        int num = foodNum;
        int i, j;
        while (num > 0) {
            i = rand.nextInt(state.getConfig().getWidth());
            j = rand.nextInt(state.getConfig().getHeight());
            if (state.getField().getCell(i, j) == -1) {
                state.addFood(new Coord(i, j, state.getConfig().getWidth(), state.getConfig().getHeight()));
                state.getField().setCell(i, j, -2);
                num--;
            }
        }

    }

    public MyField getField() {
        return state.getField();
    }

    public GState getState() {
        return state;
    }

    public SnakesProto.GameState getProtoState() {
        return state.getProtoState();
    }

    public List<GPlayer> getPlayers() {
        return state.getPlayers();
    }

    public void setConfig(SnakesProto.GameConfig config) {
        state.setConfig(config);
    }

    public void setPlayers(SnakesProto.GamePlayers players) {
        state.setPlayers(players);
    }


    public void showConfig() {
        System.out.println(state.getConfig());

    }

    public void showPlayers() {
        List<GPlayer> players = state.getPlayers();
        for (GPlayer player : players
        ) {
            System.out.println(player.getId());
            System.out.println(player.getName());
            System.out.println(player.getPort());
            System.out.println(player.getIpAddress());
            System.out.println(player.getRole());
            System.out.println(player.getScore());
        }
    }

    public void showState() {
        showConfig();
        showPlayers();
    }
}
