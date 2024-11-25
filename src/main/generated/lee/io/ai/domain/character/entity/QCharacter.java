package lee.io.ai.domain.character.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCharacter is a Querydsl query type for Character
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCharacter extends EntityPathBase<Character> {

    private static final long serialVersionUID = -2052429843L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCharacter character = new QCharacter("character");

    public final lee.io.ai.global.entity.QBaseTimeEntity _super = new lee.io.ai.global.entity.QBaseTimeEntity(this);

    public final BooleanPath anonymous = createBoolean("anonymous");

    public final StringPath characterImageUrl = createString("characterImageUrl");

    public final StringPath characterName = createString("characterName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath features = createString("features");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<lee.io.ai.domain.image.entity.Image, lee.io.ai.domain.image.entity.QImage> images = this.<lee.io.ai.domain.image.entity.Image, lee.io.ai.domain.image.entity.QImage>createList("images", lee.io.ai.domain.image.entity.Image.class, lee.io.ai.domain.image.entity.QImage.class, PathInits.DIRECT2);

    public final lee.io.ai.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QCharacter(String variable) {
        this(Character.class, forVariable(variable), INITS);
    }

    public QCharacter(Path<? extends Character> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCharacter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCharacter(PathMetadata metadata, PathInits inits) {
        this(Character.class, metadata, inits);
    }

    public QCharacter(Class<? extends Character> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new lee.io.ai.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

