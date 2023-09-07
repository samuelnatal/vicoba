const { pool } = require("../database");
function getAddresses(req, res) {
  pool.query('SELECT * FROM "address" ;', (error, results) => {
    if (error) {
      throw error;
    }
    res.json(results.rows);
  });
}

module.exports = { getAddresses };
