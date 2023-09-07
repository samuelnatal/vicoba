const { pool } = require("../database");

function getUserRequestedKikobaInfo(req, res) {
  var { userID, kikobaID } = req.body;

  //Query to retrieve information of the user who requested a particular kikoba group
  pool.query(
    'SELECT "firstName", "lastName", phone, email, gender, TO_CHAR(AGE(current_date, dob),\'YY "years"\') AS dob, region, district, ward, "userKey", "kikobaKey" FROM "address" JOIN "user" ON "addressID" = "addressKey" JOIN "member" ON "userID" = "userKey" WHERE "userKey" =' +
      userID +
      ";",
    (error, results) => {
      if (error) {
        throw error;
      }
      res.json(results.rows[0]);
    }
  );
}

module.exports = { getUserRequestedKikobaInfo };
