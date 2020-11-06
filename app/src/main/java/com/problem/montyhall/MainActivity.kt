package com.problem.montyhall

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import kotlin.concurrent.timer
import kotlin.random.Random
import kotlin.random.asJavaRandom

class MainActivity : AppCompatActivity() {

    var swap_count=0
    var noswap_count=0
    val hold_text="Pintu yang dipilih adalah pintu "
    val start_cmt="Silakan pilih pintu anda"



    lateinit var swap:Button
    lateinit var noswap:Button

    lateinit var door1:ImageButton
    lateinit var door2:ImageButton
    lateinit var door3:ImageButton
    lateinit var explain:ImageButton
    lateinit var restart:ImageButton

    lateinit var hold:TextView
    lateinit var cmt:TextView
    lateinit var pswap:TextView
    lateinit var pnoswap:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swap=findViewById(R.id.swap)
        noswap=findViewById(R.id.no_swap)

        door1=findViewById(R.id.door1)
        door2=findViewById(R.id.door2)
        door3=findViewById(R.id.door3)
        explain=findViewById(R.id.explain)
        restart=findViewById(R.id.restart)

        hold=findViewById(R.id.hold_door)
        cmt=findViewById(R.id.comment)
        pswap=findViewById(R.id.swap_count)
        pnoswap=findViewById(R.id.noswap_count)
        monty_hall.shuffel_door()

        cmt.text=start_cmt
        swap.isEnabled=false
        noswap.isEnabled=false
        swap.isVisible=false
        noswap.isVisible=false

        door1.setOnClickListener {
            pintu_dipilih(0)
        }
        door2.setOnClickListener {
            pintu_dipilih(1)
        }
        door3.setOnClickListener {
            pintu_dipilih(2)
        }

        explain.setOnClickListener {
            val intent=Intent(this, Penjelasan::class.java)
            startActivity(intent)
        }
        restart.setOnClickListener {
            restart_game()
        }




    }
    fun pintu_dipilih(pintu:Int){
        monty_hall.Choosen_door(pintu)
        door1.isEnabled=false
        door2.isEnabled=false
        door3.isEnabled=false

        hold.text=hold_text+(pintu+1)
        val timer=object :CountDownTimer(500,100){
            override fun onTick(p0: Long) {
                cmt.text="mari kita buka salah satu pintunya"
            }

            override fun onFinish() {
                buka_pintu()
            }
        }
        timer.start()

    }

    fun buka_pintu(){
        val door=monty_hall.open_door()
        if (door==0){
            door1.setImageResource(R.drawable.goat)
        }else if(door==1){
            door2.setImageResource(R.drawable.goat)
        }else if(door==2){
            door3.setImageResource(R.drawable.goat)
        }
        ganti_pintu(door)


    }

    fun ganti_pintu(dibuka:Int){
        object : CountDownTimer(6000,1000){
            override fun onTick(p0: Long) {
                cmt.text="Pintu "+(dibuka+1)+" di dalamnya adalah kambing"
            }

            override fun onFinish() {
                cmt.text="Apakah mau ganti pintu pilihan ?"
                swap.isEnabled=true
                noswap.isEnabled=true
                swap.isVisible=true
                noswap.isVisible=true
            }
        }.start()

            swap.setOnClickListener {
                if(dibuka==0 && monty_hall.door==2 || dibuka==2 && monty_hall.door==0) {
                    monty_hall.change_door(1)
                    hold.text=hold_text+2
                    swap.isEnabled=false
                    noswap.isEnabled=false
                }else if (dibuka==1 && monty_hall.door==2 || dibuka==2 && monty_hall.door==1){
                    monty_hall.change_door(0)
                    hold.text=hold_text+1
                    swap.isEnabled=false
                    noswap.isEnabled=false
                }else if (dibuka==0 && monty_hall.door==1 || dibuka==1 && monty_hall.door==0){
                    monty_hall.change_door(2)
                    hold.text=hold_text+3
                    swap.isEnabled=false
                    noswap.isEnabled=false
                }
                noswap.isEnabled=false
                val timer=object :CountDownTimer(6000,1000){
                    override fun onTick(p0: Long) {
                        cmt.text="OK, mari kita lihat pilihan yang benarnya"
                        swap.isEnabled=false
                        noswap.isEnabled=false
                    }

                    override fun onFinish() {
                        hasil(true)
                    }
                }
                timer.start()
            }
            noswap.setOnClickListener {
                swap.isEnabled=false
                val timer=object :CountDownTimer(500,100){
                    override fun onTick(p0: Long) {
                        cmt.text="OK, Tidak mengganti mari kita lihat pilihan yang benarnya"
                    }

                    override fun onFinish() {
                        hasil(false)
                    }
                }
                timer.start()
            }


    }

    fun hasil(swap_cek: Boolean) {
        if (monty_hall.hasil()==true){
            cmt.text="Selamat ada benar"
            open_prize(monty_hall.door)
            if (swap_cek==true){
                swap_count++
                pswap.text=swap_count.toString()

            } else {noswap_count++
                    pnoswap.text=noswap_count.toString()}
        }else {
            open_prize(monty_hall.car_door)
            cmt.text="Sayang sekali pilihan ada salah, selamat menikmati kambing"}

    }
    fun open_prize(pintu: Int){
        if (pintu==0){
            door1.setImageResource(R.drawable.car)
            door2.setImageResource(R.drawable.goat)
            door3.setImageResource(R.drawable.goat)
        }else if (pintu==1){
            door2.setImageResource(R.drawable.car)
            door1.setImageResource(R.drawable.goat)
            door3.setImageResource(R.drawable.goat)
        }else {
            door3.setImageResource(R.drawable.car)
            door1.setImageResource(R.drawable.goat)
            door2.setImageResource(R.drawable.goat)

        }
    }


    fun restart_game(){
        door1.setImageResource(R.drawable.door2)
        door2.setImageResource(R.drawable.door2)
        door3.setImageResource(R.drawable.door2)

        door1.isEnabled=true
        door2.isEnabled=true
        door3.isEnabled=true
        cmt.text=start_cmt
        swap.isEnabled=false
        noswap.isEnabled=false
        swap.isVisible=false
        noswap.isVisible=false


    }

}





    /*fun pintu_dipilih(pintu:Int){
        if(pintu==0){
            hold.text="pintu 1 dipilih"
            door2.isEnabled=false
            door3.isEnabled=false
            shuffel_array(pintu)

        }else if (pintu==1){
            hold.text="Pintu 2 dipilih"
            door1.isEnabled=false
            door3.isEnabled=false
            shuffel_array(pintu)
        }else if (pintu==2){
            hold.text="Pintu 3 dipilih"
            door1.isEnabled=false
            door2.isEnabled=false
            shuffel_array(pintu)
        }
    }
    fun shuffel_array(pilihan:Int) {
        val isi_pintu= listOf<String>("Car","goat1","goat2")
        val isi_pintu2=isi_pintu.shuffled()
        val car_pintu=isi_pintu2.indexOf("Car")
        buka_pintu(car_pintu, pilihan, isi_pintu2)

    }

    fun buka_pintu(car_pintu:Int , pilihan:Int , prize:List<String>) {
        var i=0
        while(i<=3){
            if(i!=car_pintu && i!=pilihan){
               break
            }else i++
        }
        if(i==0){
            door1.setImageResource(R.drawable.goat)
        }else if(i==1){
            door2.setImageResource(R.drawable.goat)
        }else if (i==2){
            door3.setImageResource(R.drawable.goat)
        }
        ganti_pintu(i, pilihan,car_pintu,prize)
    }
    fun ganti_pintu(dibuka:Int, pilihan:Int,car_pintu: Int, prize: List<String>) {
        var ganti:Int=0
        var dibuka2=dibuka
        var Bswap:Boolean=false
        cmt.text = "apakah anda mau mengganti pintu pilihan anda?"
        swap.setOnClickListener {
            Bswap=true
            noswap.isEnabled=false
            if (dibuka == 2 && pilihan == 0 || dibuka == 0 && pilihan == 2) {
                door2.isEnabled = true
                door2.isClickable=false
                if (pilihan==0){
                    door1.isEnabled=false
                }else if(pilihan==2){door3.isEnabled=false}
                ganti=1
                hasil(car_pintu,ganti,Bswap )
            } else if (dibuka == 1 && pilihan == 0 || dibuka == 0 && pilihan == 1) {
                door3.isEnabled = true
                door3.isClickable=false
                if (pilihan==0){
                    door1.isEnabled=false
                }else if(pilihan==1){door2.isEnabled=false}
                ganti=2
                hasil(car_pintu,ganti,Bswap )
            } else if (dibuka == 1 && pilihan == 2 || dibuka == 2 && pilihan == 1) {
                door1.isEnabled = true
                door1.isClickable = false
                if (pilihan==2){
                    door3.isEnabled=false
                }else if(pilihan==1){door2.isEnabled=false}
                ganti = 0
                hasil(car_pintu,ganti,Bswap )
            }
        }
        noswap.setOnClickListener {
            swap.isEnabled=false
            ganti=pilihan
            hasil(car_pintu,ganti,Bswap )
        }

    }
    fun hasil(car_pintu: Int, pintu: Int, swap: Boolean){
        if (car_pintu==pintu){
            cmt.text="selamat pintu pilihan anda benar"
            if (car_pintu==0){
                door1.setImageResource(R.drawable.car)
            }else if(car_pintu==1){
                door2.setImageResource(R.drawable.car)
            }else if (car_pintu==2){
                door1.setImageResource(R.drawable.car)
            }
            if(swap==true){
                swap_count++
                pswap.text=swap_count.toString()
            }else {
                noswap_count++
                pnoswap.text=noswap_count.toString()
            }

        }else cmt.text="sayang sekali pintu pilihan anda salah"

    }*/
