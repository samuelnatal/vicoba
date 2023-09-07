const { pool } = require("../database");

function getInvitedKikobaInfo(req, res) {
  var { kikobaID } = req.body;

  //Query to retrieve kikoba information user have been invited for
  pool.query(
    'SELECT "firstName", "lastName", "kikobaID", "kikobaName", "kikobaDesc", TO_CHAR(kikoba."createdAt",\'YYYY-MM-DD\') AS "createdAt", member."userKey" AS "kikobaAdmin", region, district, ward FROM "address" JOIN "kikoba" ON "addressID" = "kikobaLocationID" JOIN "admin" ON "kikobaID" = "kikobaKey" JOIN "member" ON "memberKey" = "memberID" JOIN "user" ON "userKey" = "userID" WHERE "kikobaID" = ' +
      kikobaID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows[0]);
    }
  );
}

module.exports = { getInvitedKikobaInfo };
