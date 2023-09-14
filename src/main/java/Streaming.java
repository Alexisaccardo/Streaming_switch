import java.sql.*;
import java.util.Scanner;

public class Streaming {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("*****WELCOME*****");

        boolean aux = true;
        while (aux) {

            System.out.println("1. registrar serie o pelicula: ");
            System.out.println("2. Eliminar serie o pelicula: ");
            System.out.println("3.Ingresar sugerencias para serie o pelicula: ");
            System.out.println("4. Terminar");
            System.out.println("Ingrese un numero entre 1 - 4: ");
            int result = Integer.parseInt(scanner.nextLine());

            switch (result) {
                case 1:

                    System.out.print("Ingrese codigo de la pelicula o serie: ");
                    String code = scanner.nextLine();

                    System.out.println("Ingrese nombre de la pelicula o serie: ");
                    String name = scanner.nextLine();

                    System.out.print("Ingrese fecha de estreno: ");
                    String day = scanner.nextLine();

                    System.out.print("Ingrese la plataforma: ");
                    String platform = scanner.nextLine();

                    System.out.println("Ingrese si es pelicula o serie: ");
                    String type = scanner.nextLine();

                    Insert(code, name, day, platform, type); //

                    break;

                case 2: 
                    System.out.println("Ingrese el codigo de la pelicula o serie a eliminar: ");
                    code = scanner.nextLine();
                    Eliminar(code);
                    
                    break;

                case 3:

                    System.out.print("Ingrese codigo de la pelicula o serie: ");
                    code = scanner.nextLine();

                    System.out.print("Ingrese sugerencias: ");
                    String suggestion = scanner.nextLine();

                    Editar(code, suggestion);

                    break;

                case 4:
                    System.out.println("Finalizando...");

                    aux = false;

                    break;

                default:
                    System.out.println("Ingrese un numero valido");
            }
        }
    }

    private static void Editar(String code, String suggestion) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/streaming";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consulta = "UPDATE plataformas SET sugerencias = ? WHERE codigo = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consulta);
        preparedStatement.setString(1, suggestion);
        preparedStatement.setString(2, code);

        int filasActualizadas = preparedStatement.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Sugerencia registrada exitosamente");
        } else {
            System.out.println("No se encontró el codigo para actualizar");
        }

        preparedStatement.close();
        connection2.close();
    }

    private static void Eliminar(String code) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/streaming";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM plataformas WHERE codigo = ?";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);
        prepareStatement.setString(1, code);
        prepareStatement.executeUpdate();

        System.out.println("Pelicula/serie eliminada correctamente");
    }

    private static void Insert (String code, String name, String day, String platform, String type){

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/streaming";
            String username = "root";
            String password = "";

            try {
                Class.forName(driver);
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM plataformas");


                // Sentencia INSERT
                String sql = "INSERT INTO plataformas (codigo, nombre, fecha, plataforma, sugerencias, tipo) VALUES (?, ?, ?, ?, ?, ?)";

                // Preparar la sentencia
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, code);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, day);
                preparedStatement.setString(4, platform);
                preparedStatement.setString(5, " ");
                preparedStatement.setString(6, type);


                // Ejecutar la sentencia
                int filasAfectadas = preparedStatement.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("pelicula/serie agregada exitosamente.");
                } else {
                    System.out.println("No se pudo agregar la pelicula/serie.");
                }

                preparedStatement.close();
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
