package lee.io.ai.domain.character.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCharacterImage is a Querydsl query type for CharacterImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCharacterImage extends EntityPathBase<CharacterImage> {

    private static final long serialVersionUID = -798868114L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCharacterImage characterImage = new QCharacterImage("characterImage");

    public final QCharacter character;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final lee.io.ai.domain.image.entity.QImage image;

    public QCharacterImage(String variable) {
        this(CharacterImage.class, forVariable(variable), INITS);
    }

    public QCharacterImage(Path<? extends CharacterImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCharacterImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCharacterImage(PathMetadata metadata, PathInits inits) {
        this(CharacterImage.class, metadata, inits);
    }

    public QCharacterImage(Class<? extends CharacterImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.character = inits.isInitialized("character") ? new QCharacter(forProperty("character"), inits.get("character")) : null;
        this.image = inits.isInitialized("image") ? new lee.io.ai.domain.image.entity.QImage(forProperty("image")) : null;
    }

}

