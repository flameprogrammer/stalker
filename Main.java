package org.flame.util.stalker;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.err.println("  ██████ ▄▄▄█████▓ ▄▄▄       ██▓     ██ ▄█▀▓█████  ██▀███  ");
        System.err.println("▒██    ▒ ▓  ██▒ ▓▒▒████▄    ▓██▒     ██▄█▒ ▓█   ▀ ▓██ ▒ ██▒");
        System.err.println("░ ▓██▄   ▒ ▓██░ ▒░▒██  ▀█▄  ▒██░    ▓███▄░ ▒███   ▓██ ░▄█ ▒");
        System.err.println("  ▒   ██▒░ ▓██▓ ░ ░██▄▄▄▄██⑧▒██░    ▓██ █▄ ▒▓█  ▄ ▒██▀▀█▄  ");
        System.err.println("▒██████▒▒  ▒██▒ ░  ▓█   ▓██▒░██████▒▒██▒ █▄░▒████▒░██▓ ▒██▒");
        System.err.println("▒ ▒▓▒ ▒ ░  ▒ ░░    ▒▒   ▓▒█░░ ▒░▓  ░▒ ▒▒ ▓▒░░ ▒░ ░░ ▒▓ ░▒▓░");
        System.err.println("░ ░▒  ░ ░    ░      ▒   ▒▒ ░░ ░ ▒  ░░ ░▒ ▒░ ░ ░  ░  ░▒ ░ ▒░");
        System.out.println("\n       --*   stalker08- Ankit Chauhan   *--         ");
        System.out.println("     --*   ⋒⋑ↈↈↈↈↈↈↈↈΨↈↈↈↈↈↈↈ⋐⋓   *--         \n ");
        System.out.println("enter hostname or ip address:");
        String hostname = scanner.nextLine();


        try{
            InetAddress add = InetAddress.getByName(hostname);

            List<Port> openPorts = new Stalkerr(add).scan();

            System.out.println("Scanned and open ports are:\n");
            for(Port p: openPorts){
                System.out.println(p.getPort() + "\t" + p.getType());
            }
        }
        catch(UnknownHostException ex){
            System.err.println("Invalid argument: " + ex.getMessage());
            System.exit(1);
        }
    }
}