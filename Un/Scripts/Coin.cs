﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Coin : MonoBehaviour
{
    private Animator anim;

    private void Start()
    {
        anim = GetComponent<Animator>();
    }

    private void OnTriggerEnter(Collider other)
    {
        if(other.tag == "Player")
        {
            GameServer.Instance.GetCoin();
            anim.SetTrigger("Collected");
            Destroy(gameObject, 1.5f);
        }
    }
}
