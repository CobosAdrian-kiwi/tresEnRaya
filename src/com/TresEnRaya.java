package com;
import java.util.Scanner;

/*
 * JUEGO DE 3 En RAYA
 *X = true & 3 / O = false & 4
 */


public class TresEnRaya {
	//MAIN
	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		//Creamos 3 tablas
		int[][] tablero_logico = { //Tablero logico es el que funciona y se hacen los calculos en
				{0,0,0},
				{0,0,0},
				{0,0,0}
		};
		char[][] tablero_estetico = new char[3][3]; //Tablero estetico es el que se muestra con las x y las o
		//Se crea una tabla para ver los controles
		char[][] controles = { //Un tablero fijo que muestra las posiciones a introducir
				{'1','2','3'},
				{'4','5','6'},
				{'7','8','9'}
		};
		
		//Se crea la variable de la opción del menú
		int opcion = 0;
		
		boolean turnoActual = true; //true (x), false(o) Determina que turno se juego¡a
		String Jugadoractual; //Un string que va a ir cambiando
		final String X = "X"; final String O = "O"; 
		
		//Creamos la variable del nº del turno actual Nos sirve para saber si el tablero está lleno
		int acumTurno = 0; 
		boolean tablero_lleno = false; //Determinar si el tablero está lleno
		
		//Creamos la tabla con las coordenadas a jugar
		int pos; //Posición que se va a traducir
		int[] coordenadas = new int[2]; //Tabla de 2 valores 0,0 , 1,0 , 0,1...
		boolean pos_rellena; //Variable para saber si una posicion ya está llena
		
		//Variable para ganador (0 = nadie | 3 = X | 4 = O)
		int ganador = 0;
		
		//Bucle del juego
		do {
			if(turnoActual == true) { //Se comprueba de quien es el turno actual y se define el jugador actual
				Jugadoractual = X;
			} else {
				Jugadoractual = O;
			}
			System.out.println("Turno de "+ Jugadoractual //Se imprime el menu junto al turno actual
					+ "\nElige la opcion que hacer:\n"
					+ "(1)Jugar\n"
					+ "(2)Ver Tablero\n"
					+ "(3)Ver Controles\n"
					+ "(4)Salir");
			opcion = sc.nextInt(); //Se determina opcion con lo que introduzca el usuario
			
			//JUEGO-----------------------------------------------------
			switch (opcion) {
			case 1: //JUGAR - se comprueba si el tablero está lleno si no lo esta se juega si lo está se dice por pantalla
				tablero_lleno = TableroLleno(acumTurno);
				if(tablero_lleno == false) {
					System.out.println("Posición a rellenar: (1-9 (si no es uno de esos valores se considerará 1))");
					pos = sc.nextInt();
					coordenadas = TraductorPos(pos); //Se mira la posicion introducida y se busca traducirla con la tabla de coordenadas
					
					pos_rellena = ComprobarPosicion(coordenadas, tablero_logico); //se comprueba si la posicion introducida está rellena
					
					//Se comprueba si se puede rellenar o no
					if(pos_rellena == false) {
						//Rellenar la tabla con la posición introducida
						tablero_logico = RellenarPosicion(coordenadas, turnoActual, tablero_logico);
						
						//Comprobar si el turno actual es de X o de O para cambiarlo debido a que ya ha jugado
						if(turnoActual == true) {
						turnoActual = false;
						}else {
						turnoActual = true;
						}
						acumTurno++; //Se le suma un turno (si llega a 9 ya se ha completado el juego)
					}else {
						System.err.println("Esa posición ya está rellena, prueba con otra"); //Si la posicion elegida está llena se dice
					}
					
				} else {
					System.out.println("El tablero está lleno... ESTO ES UN EMPATE...Pulsa 4 para salir"); //Si la tabla está llena se dice
				}
				tablero_estetico = ActualizarTableroEst(tablero_logico); //Se Actualiza y muestra el tablero estético
				MostrarTableroChar(tablero_estetico);
				
				ganador = HayGanador(tablero_logico); //Se comprueba si hay ganador
				
				///Si lo hay se muestra por pantalla y se determina que la opción va a ser 4 para romper el bucle
				if(ganador == 3) {
					System.out.println("EL JUGADOR X HA GANADO LA PARTIDA!!!!!");
					opcion = 4;
					break;
				}else if (ganador == 4) {
					System.out.println("EL JUGADOR O HA GANADO LA PARTIDA!!!!!");
					opcion = 4;
					break;
				}
				break;
			case 2: //VER TABLERO
				tablero_estetico = ActualizarTableroEst(tablero_logico);
				MostrarTableroChar(tablero_estetico);
				break;
			case 3: //VER CONTROLES
				//Mensaje para que el usuario sepa
				System.out.println("Cuando al jugar te pregunte por una celda, introduce estas instrucciones.");
				MostrarTableroChar(controles); //Se muestra la tabla de posiciones
				break;
			case 4: //SALIR
				break;
			default:
				break;
			}
			
			
		}while(opcion != 4); //Si opcion es 4 se sale del bucle
		System.out.println("Saliendo..."); //Se comunica al usuario
		sc.close();
	}
	//FUNCIONES----------------------------------------------------------------------------
	//Funcion para mostrar los controles almacenados en una tabla
	//se añade un mensaje aparte
	public static void MostrarTableroChar(char[][] matriz) {
		//Bucle para mostrar la tabla con los controles
		for (int x=0; x < matriz.length; x++) {
			  for (int y=0; y< matriz[x].length; y++) {
			    System.out.print (matriz[x][y]);
			    if (y!=matriz[x].length-1) System.out.print("\t");
			  }
			  System.out.println();
			}
	}
	
	//Mostrar el tablero lógico de int[][] (principalmente para pruebas)
	public static void MostrarTablero(int[][] matriz) {
		for (int x=0; x < matriz.length; x++) {
			  for (int y=0; y< matriz[x].length; y++) {
			    System.out.print (matriz[x][y]);
			    if (y!=matriz[x].length-1) System.out.print("\t");
			  }
			  System.out.println();
			}
	}
	
	//Calcular la posición (como solo son 9 con un switch va bien)
	public static int[] TraductorPos(int pos) {
		int[] coordenadas = new int[2];
		
		switch (pos) {
		case 1:
			coordenadas[0] = 0;
			coordenadas[1] = 0;
			break;
		case 2:
			coordenadas[0] = 1;
			coordenadas[1] = 0;
			break;
		case 3:
			coordenadas[0] = 2;
			coordenadas[1] = 0;
			break;
		case 4:
			coordenadas[0] = 0;
			coordenadas[1] = 1;
			break;
		case 5:
			coordenadas[0] = 1;
			coordenadas[1] = 1;
			break;
		case 6:
			coordenadas[0] = 2;
			coordenadas[1] = 1;
			break;
		case 7:
			coordenadas[0] = 0;
			coordenadas[1] = 2;
			break;
		case 8:
			coordenadas[0] = 1;
			coordenadas[1] = 2;
			break;
		case 9:
			coordenadas[0] = 2;
			coordenadas[1] = 2;
			break;	
		default:
			coordenadas[0] = 0;
			coordenadas[1] = 0;
			break;	
		}
		
		return coordenadas;
	}
	
	//Funcion para comprobar si la posicion introducida esta rellena
	public static boolean ComprobarPosicion(int[] pos_a_comprobar, int[][] tablero) {
		boolean esta_rellena;
		int pos1 = pos_a_comprobar[0], pos2 = pos_a_comprobar[1]; //Se cogen las coordenadas de la tabla coordenadas
		
		//Si dicha celda esta llena se devuelve como rellena
		if(tablero[pos2][pos1] == 0) {
			esta_rellena = false;
		}else {
			esta_rellena = true;
		}
		return esta_rellena;
	}
	
	//Funcion para rellenar la posicion
	public static int[][] RellenarPosicion(int[] posicion, boolean turno, int[][] tablero_log) {
		
		int pos1 = posicion[0], pos2 = posicion[1]; //se cogen las coordenadas de la tabla de coordenadas
		int num = 0;
		
		//Comprobar que turno es Recordatorio (3: X | 4: O)
		if (turno == true) {
			num = 3;
		}else if (turno == false) {
			num = 4;
		}
		
		//Rellenar la tabla
		tablero_log[pos2][pos1] = num;
		
		return tablero_log;
	}
	
	//Funcion para comprobar si hay un ganador o no
	public static int HayGanador(int[][] tablero_a_comprobar) {
		int ganador = 0; // 0 = no ganador | 1 = X ganador | 2 = O ganador
		int acum_ganador = 0; //| 9 = X | 12 = O
		boolean es0 = true, skip = false;
		
		
		//Comprobar las filas
		for (int i = 0; i < tablero_a_comprobar.length; i++) { //Se recorren las columnas
			es0 = true; //se define que es0 es true
			acum_ganador = 0; //Se reinicia el acumulador
			for(int j = 0; j < tablero_a_comprobar[0].length; j++) {
				if(tablero_a_comprobar[i][j] == 0) { //Si la celda es 0 es0 = true
					es0 = true;
				}else {
					es0 = false;
				}
				acum_ganador += tablero_a_comprobar[i][j]; //se mira la acumulacion
				
				//Si ninguna es 0 y la acumulación da 9 o 12 hay un ganador
				if(es0 == false && acum_ganador == 9) {
					ganador = 3;
					skip = true;
					break;
				}else if(es0 == false && acum_ganador == 12) {
					ganador = 4;
					skip = true;
					break;
				}
			}
			
		}
			if (skip == false) { //si ya ha habido un ganador se salta toda esta comprobacion
			//Comprobar las columnas
			for (int i = 0; i < tablero_a_comprobar[0].length; i++) {
				es0 = true;
				acum_ganador = 0;
				for(int j = 0; j < tablero_a_comprobar.length; j++) {
					if(tablero_a_comprobar[j][i] == 0) {
						es0 = true;
					}else {
						es0 = false;
					}
					acum_ganador += tablero_a_comprobar[j][i];
				
					if(es0 == false && acum_ganador == 9) {
						ganador = 3;
						skip = true;
						break;
					}else if(es0 == false && acum_ganador == 12) {
						ganador = 4;
						skip = true;
						break;
					}
				}
			}
		}
			//Comprobar diagonales
		if (skip == false) {
			if (ganador == 0) {
				acum_ganador = tablero_a_comprobar[0][0] + tablero_a_comprobar[1][1] + tablero_a_comprobar[2][2];
				if(es0 == false && acum_ganador == 9) {
					ganador = 3;
					skip = true;
				}else if(es0 == false && acum_ganador == 12) {
					ganador = 4;
					skip = true;
				}
				if (skip == false) {
					if (ganador == 0) {
						acum_ganador = tablero_a_comprobar[0][2] + tablero_a_comprobar[1][1] + tablero_a_comprobar[2][0];
						if(es0 == false && acum_ganador == 9) {
							ganador = 3;
						}else if(es0 == false && acum_ganador == 12) {
							ganador = 4;
						}
					}
				}
				
			}
		}
		
		return ganador;
	}
	
	//Función para saber si el tablero está lleno y por tanto es un empate
	public static boolean TableroLleno(int num_turnos) {
		boolean tablero_lleno = false;
		
		if(num_turnos >= 9) {
			tablero_lleno = true;
		}
		return tablero_lleno;
	}
	
	//Funcion para actualizar el tablero estético con el logico
 public static char[][] ActualizarTableroEst(int[][] tLogic){
	 char[][] tEstet = new char[3][3];
	 
	 for(int i = 0; i < tLogic.length; i++) {
		 for(int j = 0; j < tLogic[0].length; j++) {
			 if(tLogic[i][j] == 0) {
				 tEstet[i][j] = '-';
			 } else if(tLogic[i][j] == 3) {
				 tEstet[i][j] = 'x';
			 }else if (tLogic[i][j] == 4) {
				 tEstet[i][j] = 'o';
			 }
		 }
	 }
	 return tEstet;
 }
}
