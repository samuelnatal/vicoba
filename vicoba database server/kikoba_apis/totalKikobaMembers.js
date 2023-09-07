const { pool } = require("../database");

function getTotalKikobaMembers(req, res) {
  var { kikobaID } = req.body;

  //Query to retrieve a list of members participating in a particular kikoba
  pool.query(
    'SELECT COUNT("userKey") AS total FROM "member"  WHERE "kikobaKey" = ' +
      kikobaID +
      " AND status = 'Approved';",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows[0]);
    }
  );
}

module.exports = { getTotalKikobaMembers };
