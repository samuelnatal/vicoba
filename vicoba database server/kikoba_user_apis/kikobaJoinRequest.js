const { pool } = require("../database");

function saveKikobaJoinRequest(req,res) {
  var { userID, kikobaID } = req.body;

  pool.query(
    'INSERT INTO "member"  ("userKey","kikobaKey","status") VALUES (\'' +
      userID +
      "'," +
      kikobaID +
      ",'Pending');",
    (error, results) => {
      if (error) {
        throw error;
      }
    }
  );
}

module.exports = { saveKikobaJoinRequest };
