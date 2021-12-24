package basicScenarios

userId = 'NA' // custom config value defined in the default context

personas { // persona config section, similar to environments config section
    Alice {
        userId = "uid-alice" // Alice's specific config value
    }

    Bob {
        userId = "uid-bob" // Bob's specific config value
    }
}
