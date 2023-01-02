package pl.agh.edu.to.rzulfie.model.game.map;

import pl.agh.edu.to.rzulfie.model.game.utils.Vector;

public final class MapFactory {

    public static final int MAP_SIZE = 10;

    private MapFactory() {
    }

    public static GridMap sampleComplexMap() {
        MapCreator mapCreator = new MapCreator(new Vector(MAP_SIZE, MAP_SIZE));
        mapCreator.addMapField(new Vector(2, 5));
        mapCreator.markAsStartField(new Vector(2, 5));

        //      'main' way
        mapCreator.addMapField(new Vector(3, 5));
        mapCreator.addLinkBetweenFields(new Vector(2, 5), new Vector(3, 5));

        mapCreator.addMapField(new Vector(4, 5));
        mapCreator.addLinkBetweenFields(new Vector(3, 5), new Vector(4, 5));

        mapCreator.addMapField(new Vector(5, 5));
        mapCreator.addLinkBetweenFields(new Vector(4, 5), new Vector(5, 5));

        mapCreator.addMapField(new Vector(6, 5));
        mapCreator.addLinkBetweenFields(new Vector(5, 5), new Vector(6, 5));

        mapCreator.addMapField(new Vector(7, 5));
        mapCreator.addLinkBetweenFields(new Vector(6, 5), new Vector(7, 5));

        mapCreator.addMapField(new Vector(7, 4));
        mapCreator.addLinkBetweenFields(new Vector(7, 5), new Vector(7, 4));

        mapCreator.addMapField(new Vector(7, 3));
        mapCreator.addLinkBetweenFields(new Vector(7, 4), new Vector(7, 3));

        mapCreator.addMapField(new Vector(7, 2));
        mapCreator.addLinkBetweenFields(new Vector(7, 3), new Vector(7, 2));

        //      shortcut
        mapCreator.addMapField(new Vector(5, 4));
        mapCreator.addLinkBetweenFields(new Vector(5, 5), new Vector(5, 4));

        mapCreator.addMapField(new Vector(5, 3));
        mapCreator.addLinkBetweenFields(new Vector(5, 4), new Vector(5, 3));

        mapCreator.addMapField(new Vector(5, 2));
        mapCreator.addLinkBetweenFields(new Vector(5, 3), new Vector(5, 2));

        mapCreator.addMapField(new Vector(6, 2));
        mapCreator.addLinkBetweenFields(new Vector(5, 2), new Vector(6, 2));
        mapCreator.addLinkBetweenFields(new Vector(7, 2), new Vector(6, 2));

        mapCreator.addMapField(new Vector(6, 1));
        mapCreator.addLinkBetweenFields(new Vector(6, 2), new Vector(6, 1));

        mapCreator.markAsFinishField(new Vector(6, 1));

        mapCreator.addFruit(new Vector(6, 5), 10);
        mapCreator.addFruit(new Vector(7, 4), 10);
        mapCreator.addFruit(new Vector(7, 2), 10);
        mapCreator.addFruit(new Vector(5, 3), 10);

        return mapCreator.create();
    }

    public static GridMap sampleStraightLineMap() {
        MapCreator mapCreator = new MapCreator(new Vector(MAP_SIZE, 0));
        for (int x = 0; x < MAP_SIZE; x++) {
            mapCreator.addMapField(new Vector(x, 0));
        }
        mapCreator.markAsStartField(new Vector(0, 0));
        mapCreator.markAsFinishField(new Vector(MAP_SIZE - 1, 0));

        return mapCreator.create();
    }
}
