const { pool } = require("../database");

function getVikobaNearUser(req, res) {
  var { userID,userAddressID } = req.body;

  pool.query(
    'SELECT "kikobaID", "kikobaName", COALESCE(status, \'Unrequested\') AS status FROM "kikoba" LEFT JOIN (SELECT  "kikobaKey", status FROM "member" WHERE "userKey" = '+ userID +') AS "requests" ON "kikobaID" = "kikobaKey" WHERE "kikobaLocationID" = ' +
      userAddressID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}

module.exports = { getVikobaNearUser };
