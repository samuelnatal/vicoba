const { pool } = require("../database");

function getOccupations(req, res) {
  pool.query('SELECT * FROM "occupation" ;', (error, results) => {
    if (error) {
      throw error;
    }
    res.json(results.rows);
  });
}

module.exports = { getOccupations };
