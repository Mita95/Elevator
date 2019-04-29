import java.lang.invoke.ConstantCallSite;
import java.util.*;
import java.util.stream.Collectors;

public class Elevator extends Thread {

    static Elevator target = null;

    ArrayList<Integer> halts = new ArrayList<>();
    HashMap<Integer, List<Integer>> reqQueue = new HashMap<>();
    int time = 0;
    int from = 0;
    int to = 0;
    final int REACH_TIME = 5;
    final int HALT_TIME = 2;
    Direction liftDirection = Direction.UP;
    int currFloor = 0;

    public void setRoute(int from, int to) {
        List<Integer> list = reqQueue.get(from) == null ? new ArrayList<>() : reqQueue.get(from);
        if (from != to && time == 0 && from == currFloor) {
            liftDirection = from < to ? Direction.UP : Direction.DOWN;
            time = Math.abs(from - to) * REACH_TIME;
            halts.add(to);
        } else {
            Direction man = from < to ? Direction.UP : Direction.DOWN;
            if (liftDirection.equals(man)) {
                if (from > currFloor && man.equals(Direction.UP)) {
                    halts.add(to);
                } else if (from < currFloor && man.equals(Direction.DOWN)) {
                    halts.add(to);
                } else {
                    list.add(to);
                    reqQueue.putIfAbsent(from, list);
                }
            } else {
                list.add(to);
                reqQueue.putIfAbsent(from, list);
            }
        }
        //lift stable and request from different floor
        list.add(to);
        reqQueue.putIfAbsent(from, list);
        time = Math.abs(currFloor - from) * REACH_TIME;
    }

    public static Elevator getTarget() throws InterruptedException {
        if (target == null) {
            createNewTarget();
        }
        return target;

    }

    static void createNewTarget() throws InterruptedException {
        target = new Elevator();
    }

    @Override
    public void run() {
        while (true) {
            if (time != 0) {
                time--;
                if (time % REACH_TIME == 0) {
                    if (liftDirection.equals(Direction.DOWN)) {

                        halts.remove((Integer) currFloor);
                        currFloor--;
                        if (reqQueue.get(currFloor).size() > 0) {
                            int min = 0;
                            for (int i : reqQueue.get(currFloor)) {
                                if (currFloor > i) {
                                    halts.add(i);
                                    reqQueue.get(currFloor).remove((Integer) i);
                                    min = min < i ? min : i;
                                }
                            }
                            time += (min - currFloor) * REACH_TIME;
                            System.out.println("Lift opened at: " + currFloor);
                        }
                    } else {
                        halts.remove((Integer) currFloor);
                        currFloor++;
                        if (reqQueue.get(currFloor) != null && reqQueue.get(currFloor).size() > 0) {
                            int max = 0;

                            Iterator<Integer> iter = reqQueue.get(currFloor).iterator();
                            while(iter.hasNext()){
                                int i = iter.next();
                                if (currFloor < i) {
                                    halts.add(i);
                                    reqQueue.get(currFloor).remove((Integer) i);
                                    max = max < i ? i : max;
                                }
                            }
                            time += (max - currFloor) * REACH_TIME;
                            System.out.println("Lift opened at: " + currFloor);
                        }
                    }
                    System.out.println("Lift is at: " + currFloor);
                }
                System.out.println("Lift in transit");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
            } else {
//                System.out.println("Lift is at: " + currFloor);
            }
        }
    }
}