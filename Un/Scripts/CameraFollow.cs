using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraFollow : MonoBehaviour
{
    [SerializeField]
    Transform player;
    Vector3 offset;
    [SerializeField]
    private float smoothRate;

	void Start ()
    {
        offset = player.position - transform.position;
	}

    private void FixedUpdate()
    {
        Vector3 currentPos = transform.position;
        Vector3 newPos = player.position - offset;
        transform.position = Vector3.Lerp(currentPos,newPos,smoothRate);
    }
    void Update ()
    {
		
	}
}
