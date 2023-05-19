Escalabilidad:
Concern (Preocupación): A medida que la aplicación se expande en número de usuarios y funcionalidades, es necesario garantizar que el sistema pueda manejar eficientemente una demanda creciente sin degradar el rendimiento.
Tactic (Táctica): El enfoque de escalamiento horizontal se utiliza para distribuir la carga de trabajo entre múltiples servidores, evitando la sobrecarga de un solo servidor. Esto se logra añadiendo más instancias de servidores para hacer frente al aumento de la carga.
Patrón de arquitectura aplicado: Arquitectura cliente-servidor con enfoque en el escalamiento horizontal. Este enfoque permite agregar capacidad de manera flexible a medida que aumenta la demanda, sin requerir aumentos significativos de recursos en servidores individuales.
Desempeño:
Concern (Preocupación): El rendimiento del sistema es crucial para garantizar una respuesta rápida del servidor a las solicitudes de los clientes.
Tactic (Táctica): El uso de caché se utiliza para almacenar datos y respuestas del servidor, lo que permite un acceso rápido a la información solicitada sin realizar consultas adicionales al servidor.
Patrón de arquitectura aplicado: El almacenamiento en caché se utiliza para mejorar el desempeño al reducir la carga en el servidor y proporcionar respuestas rápidas a las solicitudes de los clientes.
Modificabilidad:
Concern (Preocupación): Los requisitos y la funcionalidad del sistema pueden cambiar con el tiempo, lo que requiere ajustes en la arquitectura del cliente/servidor sin afectar otras áreas de la aplicación.
Tactic (Táctica): El uso del patrón Observer se aplica para establecer una conexión entre el cliente y el servidor y notificar al cliente cuando ocurren eventos o cambios en los datos, evitando solicitudes constantes de actualizaciones al servidor.
Patrón de arquitectura aplicado: El patrón Observer se utiliza para mejorar la modificabilidad al permitir cambios en partes específicas del sistema sin afectar otras áreas. Al dividir la funcionalidad en componentes separados y utilizar interfaces, se facilita la adaptación a cambios en los requisitos y funcionalidades.
En resumen, la solución propuesta aborda los atributos de calidad de escalabilidad, desempeño y modificabilidad de la siguiente manera:

Escalabilidad: Se implementa el enfoque de escalamiento horizontal en la arquitectura cliente-servidor para distribuir la carga de trabajo entre múltiples servidores.
Desempeño: Se utiliza el almacenamiento en caché para mejorar el rendimiento al reducir las consultas adicionales al servidor.
Modificabilidad: Se emplea el patrón Observer para facilitar ajustes en la arquitectura sin afectar otras áreas, permitiendo adaptarse a cambios en los requisitos y funcionalidades.
Es importante destacar que la elección de patrones y tácticas específicas puede variar según el contexto y los requisitos del sistema, y se deben considerar cuidadosamente para lograr los mejores resultados en términos de atributos de calidad.