package com.problem.montyhall

object  monty_hall {
    val prize= listOf<String>("Car","Goat1","Goat2")
    var door:Int=0
    var car_door=0

    //constructor()

    fun shuffel_door(){
        prize.shuffled()
        car_door=prize.indexOf("Car")
    }

    fun Choosen_door( choose:Int ){
        door=choose
    }

    fun open_door():Int{
        var i=0
        while (i<=3){
            if (i!=car_door && i!=door){
                break
            }else i++
        }
        return i
    }

    fun change_door(chosen_door:Int){
        door=chosen_door
    }
    fun hasil():Boolean{
        if (door==car_door){
            return true
        }else
            return false
    }

}