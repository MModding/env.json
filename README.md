[![MModding](https://raw.githubusercontent.com/MModding/art/main/brand/mmodding_dark_banner.png)](https://mmodding.github.io/discord-link)

# env.json

[<img src="https://github.com/MModding/art/blob/main/mods/env_json/logo_named.png?raw=true" width="256" alt="env.json logo">](https://modrinth.com/mod/env.json)

## Supports

[<img src="https://github.com/JR1811/Boatism/blob/5bdbea79b11428101353c4a67ccd4b3821200a76/extrernal/promo/badges/supported_on_fabric_loader.png?raw=true" width="256" alt="Supports Fabric Loader">](https://fabricmc.net)
[<img src="https://github.com/JR1811/Boatism/blob/5bdbea79b11428101353c4a67ccd4b3821200a76/extrernal/promo/badges/supported_on_quilt_loader.png?raw=true" width="256" alt="Supports Quilt Loader">](https://quiltmc.org)

## Requirements

[<img src="https://github.com/JR1811/Boatism/blob/5bdbea79b11428101353c4a67ccd4b3821200a76/extrernal/promo/badges/requires_fabric_api.png?raw=true" width="256" alt="Requires Fabric API">](https://modrinth.com/mod/fabric-api)

## Concept

env.json is a Minecraft Library introducing a new sub json file extension format, with the goal of 
redirecting minecraft resources to other ones based on the environment context.

## Implementations

env.json doesn't provide these redirections directly, but only the operations around env.json files
and their resource reload management.

There is currently two official implementations of env.json:

- [Environment Driven Assets (EDA)](https://modrinth.com/mod/env-driven-assets)

Mod Identifier: env-driven-assets.

Environments: Client

Applies env.json operations to Minecraft Vanilla asset types.

- [Environment Driven Data (EDD)](https://modrinth.com/mod/env-driven-data)

Mod Identifier: env-driven-data.

Environments: Common & Server

Applies env.json operations to Minecraft Vanilla data types.

## The env.json Format

```json
[
  {
    "rules": [ // the primary set of rules, is an "any" type
      {
        "type": "sequence", // all rules in the sequence must pass
        "rule": [] // the rules
      },
      {
        "type": "any", // passes if at least one rule passes
        "rule": [] // the rules
      },
      {
        "type": "not", // reverses the rule
        "rule": { // the rule
          "type": "...",
          "rule": "..."
        }
      },
      {
        "type": "dimension", // passes if the current dimension matches this one
        "rule": "minecraft:overworld" // the dimension, can also be a tag
      },
      {
        "type": "biome", // passes if the current biome matches this one
        "rule": "minecraft:plains" // the biome, can also be a tag
      },
      {
        "type": "x_coord", // passes if the following operations on the x-axis are valid
        "rule": {
          "comparator": "==", // must be <, >, ==, <=, >=, =< or =>
          "value": "100" // must be an integer
        }
      },
      {
        "type": "y_coord", // passes if the following operations on the y-axis are valid
        "rule": {
          "comparator": "==", // must be <, >, ==, <=, >=, =< or =>
          "value": "100" // must be an integer
        }
      },
      {
        "type": "z_coord", // passes if the following operations on the z-axis are valid
        "rule": {
          "comparator": "==", // must be <, >, ==, <=, >=, =< or =>
          "value": "100" // must be an integer
        }
      },
      {
        "type": "submerged", // passes if the current context is surrounded by water or not
        "rule": true // true for "if it is submerged" and false for "if it is not submerged"
      },
      {
        "type": "sky", // passes if the context is above the sky limit, at or below
        "rule": "at" // must be "above", "at" or "below"
      },
      {
        "type": "water", // passes if the context is above the water level, at or below
        "rule": "at" // must be "above", "at" or "below"
      },
      {
        "type": "void", // passes if the context is above the void limit, at or below
        "rule": "at" // must be "above", "at" or "below"
      }
    ],
    "result": "minecraft:block/stone" // the redirected resource
  }
]
```

The file must be registered under this format: `redirected_resource_name-redirected_resource_extension.env.json`.

## Usage For Developers

In your `build.gradle`
```groovy
repositories {
    // ...
    maven { url 'https://jitpack.io' }
}

// ...

dependencies {
    // ...
    modImplementation "com.mmodding:env.json:${theMostBeautifulVersionYouCanFind}"
}
```

You can now get your `EnvJson` object from a `Resource` object thanks to `ExtendedResource#of(Resource)#getEnvJson`
or parse it with `EnvJson#parse(Path)` or `EnvJson#parse(InputStream)`.

## Badge For Developers

[<img src="https://github.com/MModding/art/blob/main/mods/env_json/requires_env_json.png?raw=true" width="256" alt="Requires env.json">](https://modrinth.com/mod/env.json)

`[<img src="https://github.com/MModding/art/blob/main/mods/env_json/requires_env_json.png?raw=true" width="256" alt="Requires env.json">](https://modrinth.com/mod/env.json)`

## Promotion

[<img src="https://raw.githubusercontent.com/ModFest/art/3bf66556e674d670e30f647d6a48c4e1798c21d4/badge/128h/ModFest%201.20%20Badge%20Cozy.png" alt="ModFest 1.20">](https://modfest.net/1.20)
