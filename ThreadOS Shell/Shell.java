// Prarin Behdarvandian
// 10/14/17
// CSS 430
// Program 1 Shell.java
// Assumptions:
//          user input is correctly formatted. Example: PingPong Hi 40 ;
//          user input will only exit after finishing and not within the commands


import java.io.*;
import java.util.*;


class Shell extends Thread
{
    private String cmdLine;
    private StringBuffer s;
    private Boolean runCheck;
    private int shellCount = 1;
    private int tid = 0;

    public Shell()
    {
        runCheck = true;
        cmdLine = "";
    } 

    public void run()
    {
    
        while(runCheck == true) //Run the program until runcheck proves to be false
        {
            SysLib.cout("Shell[" + shellCount + "]% "); //output

            s = new StringBuffer();
            SysLib.cin(s);
            cmdLine = s.toString();

            if (cmdLine.equals("quit") || cmdLine.equals("exit")) //exit the program if either word is used
            {
                shellCount = 1; //reset shellCounter
                runCheck = false;
                break;
            }

            // set it up so that ; and & are saved in their own element in the array
            String[] semiColonSplit = cmdLine.split("((?<=;)|(?=;)|(?<=&)|(?=&))"); 

            
            if(semiColonSplit[0].equals("&") || semiColonSplit[0].equals(";")) // Check the format at the beginning
            {
                SysLib.cout("Use proper formating \n");
            }
            else if(semiColonSplit.length == 1 || semiColonSplit.length == 2) //Execute if it is one command
            {
                tid = SysLib.exec(SysLib.stringToArgs(semiColonSplit[0]));
                if(tid < 0) //error handling
                {
                    continue;
                }
                SysLib.join();
            }
            else //Start processing the commands
            {
                for(int i = 0; i < semiColonSplit.length; i+=2) //Go through the string of arguements
                {
                    if(i+1 >= semiColonSplit.length) //If it's the last item on the list
                    {
                    if(semiColonSplit[i-1].equals("&"))// it was already handled by the previous loop
                    {
                        //do nothing
                        SysLib.join();
                    }
                    else
                    {
                        tid = SysLib.exec(SysLib.stringToArgs(semiColonSplit[i]));
                        if(tid < 0)
                        {
                            continue;
                        }
                        SysLib.join();
                    }
                    }
                else if(semiColonSplit[i+1].equals("&")) //If the command ends in an '&'
                {
                    int begin = i;
                    int end = i;
                    Boolean done = false;

                    while(!done) //Go the the array and take in all commands with an associated "&"
                    {
                        if(end+1 == semiColonSplit.length || semiColonSplit[end+1].equals(";")) //Execute last item
                        {

                            tid = SysLib.exec(SysLib.stringToArgs(semiColonSplit[end]));
                            if(tid < 0)
                            {
                                end+= 2;
                                i = end;
                                continue;
                            }
                            done = true;
                        }
                        else if(semiColonSplit[end+1].equals("&")) // If the command has an "&" continue the while loop
                        {
                           
                            tid = SysLib.exec(SysLib.stringToArgs(semiColonSplit[end]));//execute
                            if(tid < 0) //error handling
                            {
                                end += 2; //continue to next item and update for loop
                                i = end;
                                continue;
                            }

                            if(end+1 == semiColonSplit.length)
                            {
                                done = true;
                            }
                            else
                            {
                                end += 2;
                            }
                            
                        }
                    }

                    while(true) // Call all the join so tid will match the join command
                    {
                        if(tid == SysLib.join())
                        {
                            break;
                        }
                    }

                    i = end;
                }
                else if(semiColonSplit[i+1].equals(";")) //Sort through ; commands
                {
                    tid = SysLib.exec(SysLib.stringToArgs(semiColonSplit[i]));
                    if(tid < 0)
                    {
                        continue;
                    }
                    SysLib.join();
                }
            }
        }
            shellCount++; //update shellCounter
        }

        SysLib.exit();
    }
}
