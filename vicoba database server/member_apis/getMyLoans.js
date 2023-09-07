const { pool } = require("../database");

function getMyLoans(req, res) {
  var { memberID } = req.body;

  //Query to retrieve a list of members participating in a particular kikoba
  pool.query(
    'SELECT "userID" AS "borrowerUserID", "firstName" AS "borrowerFirstName", "lastName" AS "borrowerLastName","loanID","borrowerID" as "borrowerMemberID", "loanAmount", TO_CHAR("requestedAt", \'YYYY-MM-DD\') AS "requestedAt", loan.status, "statusMessage" FROM "loan" JOIN "member" ON "borrowerID" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "borrowerID" = ' +
      memberID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows);
    }
  );
}

module.exports = { getMyLoans };
