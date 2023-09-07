const { pool } = require("../database");

function getAllShares(req, res) {
  var { kikobaID } = req.body;

  //Query to retrieve shares contributed by members of a kikoba.
  pool.query(
    'SELECT "userID" AS "ownerID", "firstName" AS "ownerFirstName", "lastName" AS "ownerLastName", "shareAmount", TO_CHAR("contributedAt",\'YYYY-MM-DD\') AS date FROM "share" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE share."kikobaKey" = ' +
      kikobaID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}

module.exports = { getAllShares };
