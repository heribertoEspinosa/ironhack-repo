# IronHack Lab-11

### Part 1: Designing Cloud Infrastructure

**Descripción de la infraestructura:**

1. **Amazon EC2**:
    - Usar instancias EC2 para alojar la aplicación web
    - Configurar grupos de Auto Scaling para ajustar automáticamente la cantidad de instancias según la demanda

2. **Amazon S3**:
    - Almacenar archivos estáticos de la aplicación web, como imágenes, videos y documentos
    - Guardar copias de seguridad y registros de la aplicación

3. **Amazon VPC**:
    - Crear una red privada virtual con subredes públicas y privadas
    - Usar la subred pública para instancias EC2 accesibles desde Internet
    - Usar la subred privada para bases de datos y otros recursos internos no accesibles directamente desde Internet
    - Usar Gateways de Internet y NAT para gestionar el tráfico entrante y saliente

### Part 2: IAM Configuration

**Roles y políticas de IAM:**

1. **Roles**:
    - **Desarrollador**: Permisos para acceder a recursos necesarios para el desarrollo, como EC2, S3 y logs
    - **Administrador**: Acceso completo a todos los recursos para tareas de administración y configuración
    - **Servidor de Aplicaciones**: Permisos específicos para acceder a S3, RDS y otros servicios necesarios para el funcionamiento de la aplicación

2. **Políticas de IAM**:
    - Cada rol debe tener solo los permisos necesarios para realizar sus tareas
    - Crear políticas detalladas para controlar el acceso a recursos específicos y evitar permisos excesivos

### Part 3: Resource Management Strategy

**Estrategias para optimización y escalabilidad:**

1. **Auto Scaling**:
    - Configurar grupos de Auto Scaling para que las instancias EC2 se ajusten automáticamente según la carga de trabajo

2. **Elastic Load Balancing (ELB)**:
    - Usar ELB para distribuir el tráfico de red entrante entre varias instancias EC2, asegurando alta disponibilidad y tolerancia a fallos

3. **AWS Budgets**:
    - Implementar presupuestos y alertas para controlar y optimizar los costos
    - Monitorear gastos y uso de recursos para ajustar la infraestructura según las necesidades

### Part 4: Theoretical Implementation

**Descripción de la arquitectura:**

La arquitectura incluye los siguientes componentes principales:

1. **VPC y Subredes**:
    - Crear una VPC con subredes públicas y privadas
    - Usar la subred pública para instancias EC2 accesibles desde Internet a través de un Internet Gateway
    - Usar la subred privada para bases de datos y otros recursos internos accesibles desde las instancias EC2 a través de un NAT Gateway

2. **Instancias EC2**:
    - Usar grupos de Auto Scaling para las instancias EC2 que alojan la aplicación web, permitiendo escalar horizontalmente según la demanda

3. **Amazon S3**:
    - Almacenar archivos estáticos, copias de seguridad y registros

4. **Elastic Load Balancer**:
    - Usar ELB para distribuir el tráfico entrante entre las instancias EC2 para mejorar la disponibilidad y tolerancia a fallos

5. **IAM Roles y Políticas**:
    - Crear roles específicos para desarrolladores, administradores y servidores de aplicaciones, con políticas que siguen el principio de privilegio mínimo

6. **Escalabilidad y Optimización de Costos**:
    - Usar Auto Scaling y ELB para manejar cargas variables
    - Implementar AWS Budgets para monitorear y optimizar costos

