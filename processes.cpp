// Prarin Behdarvandian
// 10/14/17
// CSS 430
// Program 1 processes.cpp
//   

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <wait.h>
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main(int argc, char* argv[])
{
	enum {READ, WRITE}; // 0 , 1

	pid_t pid1;
	pid_t pid2;
	pid_t pid3;

	int pipe1[2];
	int pipe2[2];

	if(argc < 2 || argc > 3)
	{
		perror("Input incorrect.");
		exit(EXIT_FAILURE);
	}
	
	pid1 = fork();
	wait(NULL);

	if(pid1 == 0)//Child  //wc -l
	{
		int check = pipe(pipe2);
		if(check < 0)
		{
			perror("Bad pipe");
			exit(EXIT_FAILURE);
		}
		pid2 = fork();
		wait(NULL);
		
		if(pid2 == 0)//Grand-Child //grep
		{
			int check2 = pipe(pipe1);
			if(check < 0)
			{
				perror("Bad pipe");
				exit(EXIT_FAILURE);
			}
			pid3 = fork();
			wait(NULL);
			
			
			if(pid3 == 0)//Great-grand Child //ps -A
			{
				//cout << "Ps -A" << endl;
				close(pipe1[READ]);

				dup2(pipe1[WRITE], 1);
				//cout << "Ps -A2" << endl;
				execlp("/bin/ps", "ps", "-A", NULL);
				
			}
			

			else if (pid3 > 0) // Parent of ps - A = Grep, so execlp
			{
				//cout << "Grep" << endl;

				close(pipe2[READ]);
				dup2(pipe2[WRITE], 1);

				close(pipe1[WRITE]);
				dup2(pipe1[READ], 0);
				//cout << "Grep2" << endl;

				execlp("/bin/grep", "grep", argv[1], NULL);
			}
			else //Error handling
			{
				perror("Bad Fork");
				exit(EXIT_FAILURE);
			}

		
		}
		else if (pid2 > 0)//parent of current process. The parent of grep, which is wc -l
		{
			//cout << "WC -L" << endl;
			close(pipe2[WRITE]);
			dup2(pipe2[READ], 0);
			//cout << "WC -L2" << endl;
			execlp("/usr/bin/wc", "wc", "-l", NULL);
		}
		else // error handling
		{
			perror("Bad Fork");
			exit(EXIT_FAILURE);
		}

		
	}
	else if (pid1 > 0)//child's parent
	{
		wait(NULL);
		exit(EXIT_SUCCESS);
	}
	else//Child error handling
	{
		perror("Bad Fork");
		exit(EXIT_FAILURE);
	}
	exit(EXIT_SUCCESS);
}
