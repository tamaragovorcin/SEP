"# Sistemi elektronskog placanja" 

Payment service:
    Front             - http://localhost:3000

    Eureka server     - http://localhost:8761

    API Gateway       - http://localhost:9090

    Bitcoin Service   - port: 8081
                      - api gateway -> bitcoin-service
                      - primer -> http://localhost:9090/bitcoin-service/api/bitcoin

    PayPal Service    - port: 8082
                      - api gateway -> paypal-service
                      - primer -> http://localhost:9090/paypal-service/api/paypal

    QR Code Service   - port: 8083
                      - api gateway -> qr-code-service
                      - primer -> http://localhost:9090/qr-code-service/api/qr

    Bank Card Service - port: 8084
                      - api gateway -> bank-card-service     
                      - primer -> http://localhost:9090/bank-card-service/api/bankcard         
