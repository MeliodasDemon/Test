using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameServer : MonoBehaviour
{
    public static GameServer Instance { set; get; }

    public  bool IsDead { set; get; }
    private bool isGameStartted = false;
    private PlayerController motor;
    private const int COIN_SCORE_AMOUNT = 5;

    //Ui and the UI fields
    public Text scoreText, coinText, modifierText;
    private float score, coinScore, modifierScore;
    private int lastScoure;

    private void Awake()
    {
        Instance = this;
        modifierScore = 1;
        motor = GameObject.FindGameObjectWithTag("Player").GetComponent<PlayerController>();
        //score
        modifierText.text = "x" + modifierScore.ToString("0.0");
        coinText.text = coinScore.ToString("0");
        scoreText.text = scoreText.text = score.ToString("0");
    }

    private void Update()
    {
        if(MobileInput.Instance.Tap && !isGameStartted)
        {
            isGameStartted = true;
            motor.StartRunning();
        }
        if (isGameStartted && !IsDead)
        {
            score += (Time.deltaTime * modifierScore);
            if (lastScoure != (int)score)
            {
                lastScoure = (int)score;
                scoreText.text = score.ToString("0");
            }
        }
    }

    public void GetCoin()
    {
        coinScore++;
        coinText.text = coinScore.ToString("0");
        score += COIN_SCORE_AMOUNT;
        scoreText.text = scoreText.text = score.ToString("0");
    }

    public void UpdateModifier(float modifierAmount)
    {
        modifierScore = 1.0f + modifierAmount;
        modifierText.text = "x" + modifierScore.ToString("0.0");
    }


}
