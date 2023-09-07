const { pool } = require("../database");

function getKikobaMembers(req, res) {
  var { kikobaID } = req.body;

  //Query to retrieve a list of members participating in a particular kikoba
  pool.query(
    'SELECT "memberID", "userKey", "firstName", "lastName" FROM "member" JOIN "user" ON "userKey" = "userID" WHERE "kikobaKey" = ' +
      kikobaID +
      " AND status = 'Approved';",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}

module.exports = { getKikobaMembers };
