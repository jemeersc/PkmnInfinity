/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.objects;

import model.Place;
import model.objects.buildings.HouseBuildingObject;
import model.objects.fields.CaveFieldObject;
import model.objects.fields.FloorFieldObject;
import model.objects.fields.GrassFieldObject;
import model.objects.fields.TransportFieldObject;
import model.objects.people.SilverPersonObject;
import model.objects.walls.SmallTreeWallObject;
import model.objects.walls.RockWallObject;
import model.objects.walls.BigTreeWallObject;
import model.objects.walls.BushWallObject;
import model.objects.walls.WallBackWallObject;
import model.objects.walls.WallWallObject;
import model.objects.walls.WindowWallObject;

/**
 * This class has the task of creating object
 * @author jemeersc
 */
public class ObjectFactory {

    /**
     * Creates a new instance of an extended class of AbstractObject.
     * The type of which the object will be created depends on the given output-string.
     * @param outputName the output name of the object, which will depend the type the created object will be
     * @param p the place in which the object will be created
     * @return the newly created object
     */
    public static AbstractObject createObject(String outputName, Place p) {
        if ("grass".equals(outputName)) {
            return new GrassFieldObject();
        } else if ("cave".equals(outputName)) {
            return new CaveFieldObject();
        } else if ("smalltree".equals(outputName)) {
            return new SmallTreeWallObject();
            }else if ("floor".equals(outputName)) {
            return new FloorFieldObject();
            }else if ("transport".equals(outputName)) {
            return new TransportFieldObject();
            } else if ("bush".equals(outputName)) {
            return new BushWallObject();
            } else if ("rock".equals(outputName)) {
            return new RockWallObject();
            } else if ("bigtree".equals(outputName)) {
            return new BigTreeWallObject();
            }else if ("wall".equals(outputName)) {
            return new WallWallObject();
            }else if ("wallback".equals(outputName)) {
            return new WallBackWallObject();
            }else if ("window".equals(outputName)) {
            return new WindowWallObject();
            }else if ("house".equals(outputName)) {
            return new HouseBuildingObject();
            } else if ("silver".equals(outputName)) {
            return new SilverPersonObject(p);
        } else {
            return new EmptyObject();
        }

    }
}
