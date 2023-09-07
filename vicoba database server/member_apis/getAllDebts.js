const { pool } = require("../database");

function getAllDebts(req, res) {
  var { kikobaID } = req.body;

  //Query to retrieve shares contributed by members of a kikoba.
  pool.query(
    'SELECT "userID" AS "debtorUserID", "firstName" AS "debtorFirstName", "lastName" AS "debtorLastName", "debtID", "debtAmount", "debtDesc", "debtStatus", TO_CHAR(debt."createdAt",\'YYYY-MM-DD\') AS date FROM "debt" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE debt."kikobaKey" = ' +
      kikobaID +
      " ORDER BY debt.\"createdAt\" DESC;",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}

module.exports = { getAllDebts };
