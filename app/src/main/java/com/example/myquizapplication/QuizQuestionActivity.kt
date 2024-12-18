package com.example.myquizapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class QuizQuestionActivity  : AppCompatActivity(), View.OnClickListener {
    private  var mCurrentPosition : Int = 1
    private var mQuestionsList : ArrayList<Question>? = null
    private var mSelectOptionPosition: Int = 0
    private  var mCorrectAnswer : Int = 0

    private  var tvQuestion : TextView? = null
    private var ivImage : ImageView? = null
    private  var progressBar : ProgressBar? = null
    private  var tvProgress : TextView? = null

    private  var tvOptionOne : TextView? = null
    private  var tvOptionTwo : TextView? = null
    private  var tvOptionThree : TextView? = null
    private  var tvOptionFour : TextView? = null

    private var btnSubmit : Button? = null
    private var mUserName : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        setContentView(R.layout.activity_quiz_question)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        mQuestionsList = Constants.getQuestions()
        setQuestion()


    }

    private fun setQuestion() {


        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        defaultOptionView()

        tvQuestion?.text = question.question
        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if(mCurrentPosition == mQuestionsList!!.size){
            btnSubmit?.text = "FINISH"
        }else{
            btnSubmit?.text = "SUBMIT"
        }
    }

    private  fun defaultOptionView(){
        val options =ArrayList<TextView>()
        tvOptionOne?.let{
            options.add(0,it)
        }
        tvOptionTwo?.let{
            options.add(1,it)
        }
        tvOptionThree?.let{
            options.add(2,it)
        }
        tvOptionFour?.let{
            options.add(3,it)
        }
        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }

    }

    private  fun selectedOptionView(tv:TextView,selectedOptionNum:Int){
        defaultOptionView()
        mSelectOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_option_one -> {
                tvOptionOne?.let{
                    if(mSelectOptionPosition ==0)
                    selectedOptionView(it,1)
                }
            }
            R.id.tv_option_two -> {
                tvOptionTwo?.let{
                    if(mSelectOptionPosition ==0)
                    selectedOptionView(it,2)
                }
            }
            R.id.tv_option_three -> {
                tvOptionThree?.let{
                    if(mSelectOptionPosition ==0)
                    selectedOptionView(it,3)
                }
            }
            R.id.tv_option_four -> {
                tvOptionFour?.let{
                    if(mSelectOptionPosition ==0)
                    selectedOptionView(it,4)
                }
            }
            R.id.btn_submit -> {
                if(mSelectOptionPosition == 0){
                    mCurrentPosition++
                    if(mCurrentPosition <= mQuestionsList!!.size){
                        setQuestion()
                    }else{
                        val intent = Intent(this,Final::class.java)
                        intent.putExtra(Constants.USER_NAME,mUserName)
                        intent.putExtra(Constants.CORRECT_ANSWER,mCorrectAnswer.toString())
                        intent.putExtra(Constants.TOTAL_QUESTION,mQuestionsList!!.size.toString())
                        startActivity(intent)
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    if(question!!.correctAnswer!=mSelectOptionPosition ){
                        answerView(mSelectOptionPosition,R.drawable.wrong_option_border_bg)
                    }else mCorrectAnswer++
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
                    if(mCurrentPosition == mQuestionsList!!.size){
                        btnSubmit!!.text ="FINISH"
                    }else{
                        btnSubmit!!.text = "GO TO NEXT QUESTION"
                    }
                    mSelectOptionPosition = 0
                }
            }
        }
    }
    private fun answerView(answer:Int,drawableView:Int){
        when(answer){
            1 -> {
                tvOptionOne!!.background=ContextCompat.getDrawable(this,drawableView)
            }
            2 -> {
                tvOptionTwo!!.background=ContextCompat.getDrawable(this,drawableView)
            }
            3 -> {
                tvOptionThree!!.background=ContextCompat.getDrawable(this,drawableView)
            }
            4 -> {
                tvOptionFour!!.background=ContextCompat.getDrawable(this,drawableView)
            }
        }
    }
}