const { Pool } = require("pg");
//Creating a pool object to maintain postgres vicoba database
const pool = new Pool({
  user: "postgres",
  host: "localhost",
  database: "vicoba",
  password: "serverstation23",
  port: 5433,
});

module.exports = { pool }