/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.simulation;

/**
 *
 * @author chris, connor
 */
import java.util.ArrayList;
import java.util.Random;
import com.mycompany.Character;
import com.mycompany.Child;
import com.mycompany.CommonInfected;
import com.mycompany.Soldier;
import com.mycompany.Tank;
import com.mycompany.Teacher;

public class Simulation {
    public static void main(String[] args) {
        ArrayList<Character> survivors = new ArrayList<>();
        ArrayList<Character> zombies = new ArrayList<>();
        ArrayList<String> survivorTypes = new ArrayList<>();
        ArrayList<String> zombieTypes = new ArrayList<>();

        Random random = new Random();
        int childCount = 0, teacherCount = 0, soldierCount = 0;
        for (int i = 0; i < 5; i++) {
            int survivorType = random.nextInt(3);
            if (survivorType == 0) {
                survivors.add(new Child());
                survivorTypes.add("Child");
                childCount++;
            } else if (survivorType == 1) {
                survivors.add(new Teacher());
                survivorTypes.add("Teacher");
                teacherCount++;
            } else {
                survivors.add(new Soldier());
                survivorTypes.add("Soldier");
                soldierCount++;
            }
        }

        int commonInfectedCount = 0;
        int tankCount = 0;
        for (int i = 0; i < 9; i++) {
            int zombieType = random.nextInt(2);
            if (zombieType == 0) {
                zombies.add(new CommonInfected());
                zombieTypes.add("CommonInfected");
                commonInfectedCount++;
            } else {
                zombies.add(new Tank());
                zombieTypes.add("Tank");
                tankCount++;
            }
        }

        System.out.println("We have " + survivors.size() + " survivors trying to make it to safety (" +
                childCount + " children, " + teacherCount + " teachers, " + soldierCount + " soldiers)\n");

        System.out.println("But there are " + zombies.size() + " zombies waiting for them (" +
                commonInfectedCount + " common infected, " + tankCount + " tanks)\n");

        int survivorIndex = 0;
        int zombieIndex = 0;
        ArrayList<String> killList = new ArrayList<>();

        while (true) {
            for (int i = 0; i < survivors.size(); i++) {
                if (survivors.get(i).isAlive()) {
                    for (int j = 0; j < zombies.size(); j++) {
                        if (zombies.get(j).isAlive()) {
                            survivors.get(i).attacking(zombies.get(j));
                            if (!zombies.get(j).isAlive()) {
                                killList.add(survivorTypes.get(i) + " " + i + " killed " +
                                        zombieTypes.get(j) + " " + j);
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < zombies.size(); i++) {
                if (zombies.get(i).isAlive()) {
                    for (int j = 0; j < survivors.size(); j++) {
                        if (survivors.get(j).isAlive()) {
                            zombies.get(i).attacking(survivors.get(j));
                            if (!survivors.get(j).isAlive()) {
                                killList.add(zombieTypes.get(i) + " " + i + " killed " +
                                        survivorTypes.get(j) + " " + j);
                            }
                        }
                    }
                }
            }

            int aliveSurvivors = 0;
            for (Character survivor : survivors) {
                if (survivor.isAlive()) {
                    aliveSurvivors++;
                }
            }

            int aliveZombies = 0;
            for (Character zombie : zombies) {
                if (zombie.isAlive()) {
                    aliveZombies++;
                }
            }

            if (aliveSurvivors == 0 || aliveZombies == 0) {
                break;
            }
        }

        for (String report : killList) {
            System.out.println(report);
        }

        int survivingCount = 0;
        for (Character survivor : survivors) {
            if (survivor.isAlive()) {
                survivingCount++;
            }
        }

        if (survivingCount == 0) {
            System.out.println("None of the survivors made it.");
        } else {
            System.out.println(survivingCount + " survivors made it to safety.");
        }
    }
}
