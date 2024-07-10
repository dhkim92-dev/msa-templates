const { default: axios } = require("axios");
const express = require("express");
const app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const { createAdapter } = require("@socket.io/redis-adapter");
const Redis = require("ioredis")
const os = require("os")
const {Kafka} = require("kafkajs")

console.log("SERVICE_PORT : " + process.env.SERVICE_PORT)
console.log("API_SERVER_URL : " + process.env.API_SERVER_URL)
console.log("REDIS HOST : " + process.env.REDIS_HOST)
console.log("REDIS PORT : " + process.env.REDIS_PORT)

const pubClient = new Redis({
  host: process.env.REDIS_HOST,
  port: process.env.REDIS_PORT,
  // password: process.env.REDIS_PASSWORD
})
const subClient = pubClient.duplicate()

// kafka 구독

const kafka = new Kafka({
  clientId: "socket-io-server",
  brokers: [process.env.KAFKA_BROKER]
})

const consumer = kafka.consumer({groupId: "notification-event-consumer"})

const runKafkaConsumer = async () => {
  await consumer.connect()
  await consumer.subscribe({topic: "notification-event", fromBeginning: false})

  await consumer.run({
    eachMessage: async ({topic, partition, message}) => {
      console.log("kafka event receive !")
      console.log("from partition : " + partition + " message :" + message.value)
      const parsed = JSON.parse(message.value.toString())
      const roomName = "notification/"+parsed.receiver
      console.log("emit RoomName: " + roomName)

      io.to(roomName).emit("notification", message.value.toString())
    }
  })
}

runKafkaConsumer().catch((err)=>{

})

const io = new Server(server, {
  path: "/socket",
  cors : {
    methods: ["POST", "GET", "PUT", "DELETE", "OPTIONS"],
    // origin: "*"
    origin: "http://127.0.0.1:31080",
    // credentials: true
  }
});

//adapter 

io.adapter(createAdapter(pubClient, subClient));

// middleware, authentication process

server.listen(process.env.SERVICE_PORT || 80, () => {
  console.log('listening on ' + getIPAddress() + " : " + process.env.SERVICE_PORT);
});

io.use((sock, next) => {
  console.log("connection middleware work")
  const token = sock.handshake.query.token
  
  if(!token) {
    console.log("token not provided")
    return next(new Error("Authentication failed. Token not provided."))
  }

  axios.post(process.env.API_SERVER_URL + "/api/v1/validation/jwt", 
    {
      token: token
    }
  ).then((res)=>{
    sock.jwt = token;
    sock.decoded = res.data;
    console.log("res.data : " + JSON.stringify(res.data));
    next();
  }).catch((err) => {
    console.error("authentication failed failed.");
    next(new Error("Authentication failed."));
  })
})

// const connection = new Map()

io.on("connection", async (sock) => {
    console.log("new connection request.")
    
    if(!sock.decoded.nickname) {
      sock.disconnect()
    }
    const roomName = "notification/"+sock.decoded.nickname
    console.log("connection id : " + sock.id + " join room " + roomName)
    sock.join(roomName)

    sock.on('disconnect', (reason) => {
        console.log("User disconnected. " + reason.toString());
        sock.leave(roomName)
    });
});

function getIPAddress() {
  var interfaces = os.networkInterfaces();

  for (var devName in interfaces) {
      var iface = interfaces[devName];

      for (var i = 0; i < iface.length; i++) {
          var alias = iface[i];
          if (alias.family === 'IPv4' && alias.address !== '127.0.0.1' && !alias.internal)
              return alias.address;
      }
  }

  return '0.0.0.0';
}


const disconnectSocket = (socketId) => {
  const socket = io.sockets.sockets.get(socketId)

  if(socket) {
    socket.disconnect(true);
    console.log("User " + nickname + " socket id : " + socketId + " is disconnected")
  }
}

